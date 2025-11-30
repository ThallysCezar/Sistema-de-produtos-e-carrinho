import { Component, inject, OnInit, ChangeDetectorRef, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subject, takeUntil } from 'rxjs';
import { ProductService } from './services/product.service';
import { OrderService } from './services/order.service';
import { CartService } from './services/cart.service';
import { NotificationService } from './services/notification.service';
import { Product as ProductModel } from './models/product.model';
import { CartItem } from './models/cart.model';
import { CheckoutRequest } from './models/checkout.model';

@Component({
  selector: 'app-product',
  standalone: false,
  templateUrl: './product.html',
  styleUrl: './product.scss',
})
export class Product implements OnInit, AfterViewInit, OnDestroy {
  dialog = inject(MatDialog);
  dataSource = new MatTableDataSource<ProductModel>([]);
  columnsToDisplay = ['name', 'price', 'stock'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'actions', 'expand'];
  expandedProduct: ProductModel | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  private destroy$ = new Subject<void>();

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
    private cartService: CartService,
    private notificationService: NotificationService,
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
    this.subscribeToNotifications();
    this.subscribeToCart();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private subscribeToNotifications(): void {
    this.notificationService.getErrorNotifications()
      .pipe(takeUntil(this.destroy$))
      .subscribe(notification => {
        this.errorTitle = notification.title;
        this.errorNotificationMessage = notification.message;
        this.showErrorNotification = true;
      });

    this.notificationService.getCrudNotifications()
      .pipe(takeUntil(this.destroy$))
      .subscribe(notification => {
        this.crudMessage = notification.message;
        this.showCrudNotification = true;
        setTimeout(() => {
          this.closeCrudNotification();
        }, 3000);
      });

    this.notificationService.getOrderNotifications()
      .pipe(takeUntil(this.destroy$))
      .subscribe(notification => {
        this.orderId = notification.orderId;
        this.successMessage = notification.message;
        this.showSuccessNotification = true;
      });
  }

  private subscribeToCart(): void {
    this.cartService.cartItems$
      .pipe(takeUntil(this.destroy$))
      .subscribe(items => {
        this.cartItems = items;
      });
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
        this.notificationService.showError('Erro ao Carregar Produtos', error.message);
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
    const currentProduct = this.dataSource.data.find(p => p.id === product.id);
    const availableStock = currentProduct ? currentProduct.stock : product.stock;
    const productToAdd = currentProduct || product;

    const result = this.cartService.addToCart(productToAdd, availableStock);
    
    if (!result.success && result.message) {
      this.notificationService.showError(
        result.message.includes('sem estoque') ? 'Produto Indisponível' : 'Estoque Insuficiente',
        result.message
      );
    }
  }

  removeFromCart(item: CartItem) {
    this.cartService.removeFromCart(item);
  }

  decreaseQuantity(item: CartItem) {
    this.cartService.decreaseQuantity(item);
  }

  increaseQuantity(item: CartItem) {
    const currentProduct = this.dataSource.data.find(p => p.id === item.product.id);
    const availableStock = currentProduct ? currentProduct.stock : item.product.stock;

    const result = this.cartService.increaseQuantity(item, availableStock);
    
    if (!result.success && result.message) {
      this.notificationService.showError('Estoque Insuficiente', result.message);
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
    const validationError = this.validateProductForm();
    if (validationError) {
      this.notificationService.showError(validationError.title, validationError.message);
      return;
    }

    const formValue = this.productForm.value;
    this.isLoading = true;

    if (this.editingProduct && this.editingProduct.id) {
      this.updateProduct(this.editingProduct.id, formValue);
    } else {
      this.createProduct(formValue);
    }
  }

  private validateProductForm(): { title: string; message: string } | null {
    if (this.productForm.invalid) {
      return { title: 'Formulário Inválido', message: 'Preencha todos os campos obrigatórios corretamente!' };
    }

    const formValue = this.productForm.value;

    if (formValue.price < 0) {
      return { title: 'Preço Inválido', message: 'O preço não pode ser negativo!' };
    }

    if (formValue.stock < 0) {
      return { title: 'Estoque Inválido', message: 'O estoque não pode ser negativo!' };
    }

    return null;
  }

  private updateProduct(id: number, productData: any): void {
    this.productService.updateProduct(id, productData).subscribe({
      next: (updatedProduct) => {
        this.updateProductInDataSource(updatedProduct);
        this.isLoading = false;
        this.closeProductModal();
        this.notificationService.showSuccess('O produto foi atualizado com sucesso!');
      },
      error: (error) => {
        this.isLoading = false;
        this.notificationService.showError('Erro ao Atualizar', error.message);
      }
    });
  }

  private createProduct(productData: any): void {
    this.productService.createProduct(productData).subscribe({
      next: (newProduct) => {
        this.addProductToDataSource(newProduct);
        this.isLoading = false;
        this.closeProductModal();
        this.notificationService.showSuccess('O produto foi criado com sucesso!');
      },
      error: (error) => {
        this.isLoading = false;
        this.notificationService.showError('Erro ao Criar Produto', error.message);
      }
    });
  }

  deleteProduct(product: ProductModel) {
    if (!product.id) return;

    if (confirm(`Tem certeza que deseja deletar o produto "${product.name}"?`)) {
      this.isLoading = true;

      this.productService.deleteProduct(product.id).subscribe({
        next: () => {
          this.removeProductFromDataSource(product.id!);
          this.isLoading = false;
          this.notificationService.showSuccess(`O produto "${product.name}" foi removido com sucesso!`);
        },
        error: (error) => {
          this.isLoading = false;
          this.notificationService.showError('Erro ao Deletar', error.message);
        }
      });
    }
  }

  private updateProductInDataSource(product: ProductModel): void {
    const index = this.dataSource.data.findIndex(p => p.id === product.id);
    if (index > -1) {
      this.dataSource.data[index] = product;
      this.dataSource._updateChangeSubscription();
      this.syncPaginator();
    }
  }

  private addProductToDataSource(product: ProductModel): void {
    const currentData = this.dataSource.data;
    currentData.push(product);
    this.dataSource.data = currentData;
    this.syncPaginator();
  }

  private removeProductFromDataSource(productId: number): void {
    const index = this.dataSource.data.findIndex(p => p.id === productId);
    if (index > -1) {
      const currentData = this.dataSource.data;
      currentData.splice(index, 1);
      this.dataSource.data = currentData;
      this.syncPaginator();
    }
  }

  private syncPaginator(): void {
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
    }
  }

  getCartTotal(): number {
    return this.cartService.getCartTotal();
  }

  getCartItemsCount(): number {
    return this.cartService.getCartItemsCount();
  }

  finalizePurchase() {
    if (this.cartService.isEmpty()) {
      this.notificationService.showError('Carrinho Vazio', 'Adicione produtos ao carrinho antes de finalizar a compra.');
      return;
    }

    if (this.isLoading) {
      return;
    }

    const cartItems = this.cartService.getCartItems();
    const checkoutRequest: CheckoutRequest = {
      items: cartItems.map(item => ({
        productId: item.product.id!,
        quantity: item.quantity
      }))
    };

    this.isLoading = true;

    this.orderService.checkout(checkoutRequest).subscribe({
      next: (order) => {
        this.updateProductStockAfterCheckout(cartItems);
        
        this.cartService.clearCart();
        this.isCartOpen = false;
        this.isLoading = false;

        if (order.id && order.total !== undefined) {
          this.notificationService.showOrderSuccess(order.id, order.total);
        }
      },
      error: (error) => {
        this.isLoading = false;
        this.isCartOpen = false;
        this.notificationService.showError('Erro ao Finalizar Compra', error.message);
      }
    });
  }

  private updateProductStockAfterCheckout(cartItems: CartItem[]): void {
    cartItems.forEach(cartItem => {
      const productIndex = this.dataSource.data.findIndex(p => p.id === cartItem.product.id);
      if (productIndex > -1) {
        this.dataSource.data[productIndex].stock -= cartItem.quantity;
      }
    });
    
    this.dataSource._updateChangeSubscription();
    
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
    }
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

  closeErrorNotification() {
    this.showErrorNotification = false;
    this.errorTitle = '';
    this.errorNotificationMessage = '';
  }

  closeCrudNotification() {
    this.showCrudNotification = false;
    this.crudMessage = '';
  }
}
