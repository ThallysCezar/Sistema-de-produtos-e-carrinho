import { Component, inject, OnInit, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ProductService } from './services/product.service';
import { OrderService } from './services/order.service';
import { Product as ProductModel } from './models/product.model';
import { CartItem } from './models/cart.model';
import { CheckoutRequest } from './models/checkout.model';

@Component({
  selector: 'app-product',
  standalone: false,
  templateUrl: './product.html',
  styleUrl: './product.scss',
})
export class Product implements OnInit, AfterViewInit {
  dialog = inject(MatDialog);
  dataSource = new MatTableDataSource<ProductModel>([]);
  columnsToDisplay = ['name', 'price', 'stock'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'actions', 'expand'];
  expandedProduct: ProductModel | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  isCartOpen = false;
  cartItems: CartItem[] = [];

  isProductModalOpen = false;
  productForm: FormGroup;
  editingProduct: ProductModel | null = null;

  isLoading = false;
  errorMessage = '';

  showSuccessNotification = false;
  successMessage = '';
  orderId: number | undefined = undefined;

  showCrudNotification = false;
  crudMessage = '';

  showErrorNotification = false;
  errorNotificationMessage = '';
  errorTitle = '';

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private orderService: OrderService,
    private cdr: ChangeDetectorRef
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      stock: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit() {
    setTimeout(() => {
      this.loadProducts();
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  loadProducts() {
    this.errorMessage = '';

    this.productService.getAllProducts().subscribe({
      next: (products) => {
        this.dataSource.data = products;
        // Garantir que o paginator está sincronizado
        if (this.paginator) {
          this.dataSource.paginator = this.paginator;
        }
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.errorMessage = error.message;
        this.isLoading = false;
        this.showError('Erro ao Carregar Produtos', error.message);
        this.cdr.detectChanges();
      }
    });
  }

  isExpanded(product: ProductModel) {
    return this.expandedProduct === product;
  }

  toggle(product: ProductModel) {
    this.expandedProduct = this.isExpanded(product) ? null : product;
  }

  toggleCart() {
    this.isCartOpen = !this.isCartOpen;
  }

  addToCart(product: ProductModel) {
    // Buscar o produto atualizado do dataSource para ter o estoque real
    const currentProduct = this.dataSource.data.find(p => p.id === product.id);
    const availableStock = currentProduct ? currentProduct.stock : product.stock;

    if (!availableStock || availableStock <= 0) {
      this.showError('Produto Indisponível', 'Este produto está sem estoque no momento.');
      return;
    }

    const existingItem = this.cartItems.find(item => item.product.id === product.id);

    if (existingItem) {
      if (existingItem.quantity >= availableStock) {
        this.showError('Estoque Insuficiente', `Apenas ${availableStock} unidades disponíveis. Você já tem essa quantidade no carrinho.`);
        return;
      }
      existingItem.quantity++;
      // Atualizar referência do produto no carrinho com estoque atual
      existingItem.product = currentProduct || product;
    } else {
      this.cartItems.push({
        product: currentProduct || product,
        quantity: 1
      });
    }
  }

  removeFromCart(item: CartItem) {
    const index = this.cartItems.indexOf(item);
    if (index > -1) {
      this.cartItems.splice(index, 1);
    }
  }

  decreaseQuantity(item: CartItem) {
    if (item.quantity > 1) {
      item.quantity--;
    } else {
      this.removeFromCart(item);
    }
  }

  increaseQuantity(item: CartItem) {
    // Buscar o produto atualizado do dataSource para ter o estoque real
    const currentProduct = this.dataSource.data.find(p => p.id === item.product.id);
    const availableStock = currentProduct ? currentProduct.stock : item.product.stock;

    if (item.quantity < availableStock) {
      item.quantity++;
    } else {
      this.showError('Estoque Insuficiente', `Apenas ${availableStock} unidades disponíveis em estoque.`);
    }
  }

  openAddProductModal() {
    this.editingProduct = null;
    this.productForm.reset({ name: '', price: 0, stock: 0 });
    this.isProductModalOpen = true;
  }

  openEditProductModal(product: ProductModel) {
    this.editingProduct = product;
    this.productForm.patchValue({
      name: product.name,
      price: product.price,
      stock: product.stock
    });
    this.isProductModalOpen = true;
  }

  closeProductModal() {
    this.isProductModalOpen = false;
    this.editingProduct = null;
    this.productForm.reset();
  }

  saveProduct() {
    if (this.productForm.invalid) {
      this.showError('Formulário Inválido', 'Preencha todos os campos obrigatórios corretamente!');
      return;
    }

    const formValue = this.productForm.value;

    if (formValue.price < 0) {
      this.showError('Preço Inválido', 'O preço não pode ser negativo!');
      return;
    }

    if (formValue.stock < 0) {
      this.showError('Estoque Inválido', 'O estoque não pode ser negativo!');
      return;
    }

    this.isLoading = true;

    if (this.editingProduct && this.editingProduct.id) {
      this.productService.updateProduct(this.editingProduct.id, formValue).subscribe({
        next: (updatedProduct) => {
          const index = this.dataSource.data.findIndex(p => p.id === updatedProduct.id);
          if (index > -1) {
            // Atualizar diretamente o item mantendo a posição
            this.dataSource.data[index] = updatedProduct;
            // Forçar atualização da tabela sem mudar ordem
            this.dataSource._updateChangeSubscription();
            // Garantir que o paginator está sincronizado
            if (this.paginator) {
              this.dataSource.paginator = this.paginator;
            }
          }
          this.isLoading = false;
          this.closeProductModal();
          this.showSuccess('Produto Atualizado', 'O produto foi atualizado com sucesso!');
        },
        error: (error) => {
          this.isLoading = false;
          this.showError('Erro ao Atualizar', error.message);
        }
      });
    } else {
      this.productService.createProduct(formValue).subscribe({
        next: (newProduct) => {
          // Adicionar no final da lista mantendo ordem
          const currentData = this.dataSource.data;
          currentData.push(newProduct);
          this.dataSource.data = currentData;
          // Garantir que o paginator está sincronizado
          if (this.paginator) {
            this.dataSource.paginator = this.paginator;
          }
          this.isLoading = false;
          this.closeProductModal();
          this.showSuccess('Produto Criado', 'O produto foi criado com sucesso!');
        },
        error: (error) => {
          this.isLoading = false;
          this.showError('Erro ao Criar Produto', error.message);
        }
      });
    }
  }

  deleteProduct(product: ProductModel) {
    if (!product.id) return;

    if (confirm(`Tem certeza que deseja deletar o produto "${product.name}"?`)) {
      this.isLoading = true;

      this.productService.deleteProduct(product.id).subscribe({
        next: () => {
          const index = this.dataSource.data.findIndex(p => p.id === product.id);
          if (index > -1) {
            // Remover mantendo referência do array
            const currentData = this.dataSource.data;
            currentData.splice(index, 1);
            this.dataSource.data = currentData;
            // Garantir que o paginator está sincronizado
            if (this.paginator) {
              this.dataSource.paginator = this.paginator;
            }
          }
          this.isLoading = false;
          this.showSuccess('Produto Deletado', `O produto "${product.name}" foi removido com sucesso!`);
        },
        error: (error) => {
          this.isLoading = false;
          this.showError('Erro ao Deletar', error.message);
        }
      });
    }
  }

  getCartTotal(): number {
    return this.cartItems.reduce((total, item) => total + (item.product.price * item.quantity), 0);
  }

  getCartItemsCount(): number {
    return this.cartItems.reduce((count, item) => count + item.quantity, 0);
  }

  finalizePurchase() {
    if (this.cartItems.length === 0) {
      this.showError('Carrinho Vazio', 'Adicione produtos ao carrinho antes de finalizar a compra.');
      return;
    }

    if (this.isLoading) {
      return;
    }

    const checkoutRequest: CheckoutRequest = {
      items: this.cartItems.map(item => ({
        productId: item.product.id!,
        quantity: item.quantity
      }))
    };

    this.isLoading = true;

    this.orderService.checkout(checkoutRequest).subscribe({
      next: (order) => {
        // Atualizar estoque dos produtos afetados localmente
        this.cartItems.forEach(cartItem => {
          const productIndex = this.dataSource.data.findIndex(p => p.id === cartItem.product.id);
          if (productIndex > -1) {
            this.dataSource.data[productIndex].stock -= cartItem.quantity;
          }
        });
        
        // Forçar atualização da tabela mantendo ordem
        this.dataSource._updateChangeSubscription();
        // Garantir que o paginator está sincronizado
        if (this.paginator) {
          this.dataSource.paginator = this.paginator;
        }
        
        this.cartItems = [];
        this.isCartOpen = false;
        this.isLoading = false;

        this.orderId = order.id;
        this.successMessage = `Pedido #${order.id} realizado com sucesso!\nTotal: R$ ${order.total.toFixed(2)}`;
        this.showSuccessNotification = true;
      },
      error: (error) => {
        this.isLoading = false;
        this.isCartOpen = false;
        this.showError('Erro ao Finalizar Compra', error.message);
      }
    });
  }

  closeSuccessNotification() {
    this.showSuccessNotification = false;
    this.successMessage = '';
    this.orderId = undefined;
  }

  continueShopping() {
    this.showSuccessNotification = false;
    this.successMessage = '';
    this.orderId = undefined;
  }

  showError(title: string, message: string) {
    this.errorTitle = title;
    this.errorNotificationMessage = message;
    this.showErrorNotification = true;
  }

  closeErrorNotification() {
    this.showErrorNotification = false;
    this.errorTitle = '';
    this.errorNotificationMessage = '';
  }

  showSuccess(title: string, message: string) {
    this.crudMessage = message;
    this.showCrudNotification = true;
    
    // Fechar automaticamente após 3 segundos
    setTimeout(() => {
      this.closeCrudNotification();
    }, 3000);
  }

  closeCrudNotification() {
    this.showCrudNotification = false;
    this.crudMessage = '';
  }
}
