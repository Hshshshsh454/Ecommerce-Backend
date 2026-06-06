package com.example.ecommerce.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public OrderResponse create(@RequestBody CreateOrderRequest request) {
        return service.createOrder(request);
    }
}
