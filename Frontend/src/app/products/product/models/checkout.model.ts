export interface CheckoutItemRequest {
  productId: number;
  quantity: number;
}

export interface CheckoutRequest {
  items: CheckoutItemRequest[];
}
