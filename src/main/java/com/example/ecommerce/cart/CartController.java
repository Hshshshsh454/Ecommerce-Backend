package com.example.ecommerce.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping("/{cartId}/items")
    public String addToCart(
            @PathVariable Long cartId,
            @RequestBody AddToCartRequest request) {

        service.addProduct(cartId, request);
        return "Product Added";
    }
}
