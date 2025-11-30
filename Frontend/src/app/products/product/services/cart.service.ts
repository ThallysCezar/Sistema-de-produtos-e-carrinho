import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CartItem } from '../models/cart.model';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems: CartItem[] = [];
  private cartItemsSubject = new BehaviorSubject<CartItem[]>([]);
  
  cartItems$: Observable<CartItem[]> = this.cartItemsSubject.asObservable();

  getCartItems(): CartItem[] {
    return this.cartItems;
  }

  addToCart(product: Product, availableStock: number): { success: boolean; message?: string } {
    if (!availableStock || availableStock <= 0) {
      return {
        success: false,
        message: 'Este produto está sem estoque no momento.'
      };
    }

    const existingItem = this.cartItems.find(item => item.product.id === product.id);

    if (existingItem) {
      if (existingItem.quantity >= availableStock) {
        return {
          success: false,
          message: `Apenas ${availableStock} unidades disponíveis. Você já tem essa quantidade no carrinho.`
        };
      }
      existingItem.quantity++;
      existingItem.product = product; // Atualizar referência com estoque atual
    } else {
      this.cartItems.push({
        product: product,
        quantity: 1
      });
    }

    this.cartItemsSubject.next(this.cartItems);
    return { success: true };
  }

  removeFromCart(item: CartItem): void {
    const index = this.cartItems.indexOf(item);
    if (index > -1) {
      this.cartItems.splice(index, 1);
      this.cartItemsSubject.next(this.cartItems);
    }
  }

  decreaseQuantity(item: CartItem): void {
    if (item.quantity > 1) {
      item.quantity--;
      this.cartItemsSubject.next(this.cartItems);
    } else {
      this.removeFromCart(item);
    }
  }

  increaseQuantity(item: CartItem, availableStock: number): { success: boolean; message?: string } {
    if (item.quantity < availableStock) {
      item.quantity++;
      this.cartItemsSubject.next(this.cartItems);
      return { success: true };
    } else {
      return {
        success: false,
        message: `Apenas ${availableStock} unidades disponíveis em estoque.`
      };
    }
  }

  getCartTotal(): number {
    return this.cartItems.reduce((total, item) => total + (item.product.price * item.quantity), 0);
  }

  getCartItemsCount(): number {
    return this.cartItems.reduce((count, item) => count + item.quantity, 0);
  }

  clearCart(): void {
    this.cartItems = [];
    this.cartItemsSubject.next(this.cartItems);
  }

  isEmpty(): boolean {
    return this.cartItems.length === 0;
  }
}
