import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CheckoutRequest } from '../models/checkout.model';
import { Order } from '../models/order.model';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = `${environment.apiUrl}/cart`;

  constructor(private http: HttpClient) {}

  /**
   * Realiza o checkout do carrinho
   */
  checkout(checkoutRequest: CheckoutRequest): Observable<Order> {
    return this.http.post<Order>(`${this.apiUrl}/checkout`, checkoutRequest).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Lista todos os pedidos
   */
  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/orders`).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Busca um pedido pelo ID
   */
  getOrderById(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.apiUrl}/orders/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Tratamento de erros HTTP
   */
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Ocorreu um erro desconhecido';

    if (error.error instanceof ErrorEvent) {
      // Erro do lado do cliente
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      // Erro do lado do servidor
      if (error.status === 400 && error.error) {
        // Erros de validação do backend
        if (typeof error.error === 'string') {
          errorMessage = error.error;
        } else if (error.error.message) {
          errorMessage = error.error.message;
        } else if (error.error.errors) {
          // Múltiplos erros de validação
          errorMessage = Object.values(error.error.errors).join(', ');
        }
      } else if (error.status === 404) {
        errorMessage = 'Pedido não encontrado';
      } else if (error.status === 422) {
        // Erro de estoque ou validação de negócio
        errorMessage = error.error.message || 'Erro ao processar pedido';
      } else if (error.status === 500) {
        errorMessage = 'Erro interno do servidor';
      } else if (error.error?.message) {
        errorMessage = error.error.message;
      }
    }

    return throwError(() => new Error(errorMessage));
  }
}
