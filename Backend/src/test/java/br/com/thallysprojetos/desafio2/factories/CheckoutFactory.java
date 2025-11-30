package br.com.thallysprojetos.desafio2.factories;

import br.com.thallysprojetos.desafio2.dtos.CheckoutItemDTO;
import br.com.thallysprojetos.desafio2.dtos.CheckoutRequestDTO;

import java.util.Arrays;

public class CheckoutFactory {

    public static CheckoutRequestDTO createCheckoutRequest() {
        CheckoutItemDTO item1 = createCheckoutItem(1L, 1);
        CheckoutItemDTO item2 = createCheckoutItem(2L, 2);
        
        CheckoutRequestDTO request = new CheckoutRequestDTO();
        request.setItems(Arrays.asList(item1, item2));
        return request;
    }

    public static CheckoutRequestDTO createSingleItemCheckoutRequest() {
        CheckoutItemDTO item = createCheckoutItem(1L, 2);
        
        CheckoutRequestDTO request = new CheckoutRequestDTO();
        request.setItems(Arrays.asList(item));
        return request;
    }

    public static CheckoutRequestDTO createCheckoutRequestWithExcessiveQuantity() {
        CheckoutItemDTO item = createCheckoutItem(1L, 20);
        
        CheckoutRequestDTO request = new CheckoutRequestDTO();
        request.setItems(Arrays.asList(item));
        return request;
    }

    public static CheckoutItemDTO createCheckoutItem(Long productId, Integer quantity) {
        CheckoutItemDTO item = new CheckoutItemDTO();
        item.setProductId(productId);
        item.setQuantity(quantity);
        return item;
    }

}
