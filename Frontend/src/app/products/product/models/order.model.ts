export interface OrderItem {
  id?: number;
  productId: number;
  productName: string;
  quantity: number;
  price: number;
}

export interface Order {
  id?: number;
  items: OrderItem[];
  total: number;
  createdAt: string;
}
