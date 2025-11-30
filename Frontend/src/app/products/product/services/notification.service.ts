import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Notification, OrderNotification } from '../models/notification.model';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private errorNotification$ = new Subject<Notification>();
  private crudNotification$ = new Subject<Notification>();
  private orderNotification$ = new Subject<OrderNotification>();

  getErrorNotifications() {
    return this.errorNotification$.asObservable();
  }

  getCrudNotifications() {
    return this.crudNotification$.asObservable();
  }

  getOrderNotifications() {
    return this.orderNotification$.asObservable();
  }

  showError(title: string, message: string): void {
    this.errorNotification$.next({
      title,
      message,
      type: 'error'
    });
  }

  showSuccess(message: string): void {
    this.crudNotification$.next({
      title: 'Sucesso!',
      message,
      type: 'success'
    });
  }

  showOrderSuccess(orderId: number, total: number): void {
    this.orderNotification$.next({
      title: 'Compra Finalizada com Sucesso! 🎉',
      message: `Pedido #${orderId} realizado com sucesso!\nTotal: R$ ${total.toFixed(2)}`,
      type: 'success',
      orderId,
      total
    });
  }
}
