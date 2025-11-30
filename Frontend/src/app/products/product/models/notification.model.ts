export interface Notification {
  title: string;
  message: string;
  type: 'success' | 'error' | 'info';
}

export interface OrderNotification extends Notification {
  orderId: number;
  total: number;
}
