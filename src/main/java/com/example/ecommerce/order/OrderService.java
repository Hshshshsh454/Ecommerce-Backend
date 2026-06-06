package com.example.ecommerce.order;

import com.example.ecommerce.cart.CartItem;
import com.example.ecommerce.cart.CartItemRepository;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.inventory.InventoryService;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final InventoryService inventoryService;

    public OrderResponse createOrder(
            CreateOrderRequest request){

        List<CartItem> items =
                cartItemRepository.findAll();

        if(items.isEmpty()){

            throw new BusinessException(
                    "Cart Empty");
        }

        BigDecimal total =
                BigDecimal.ZERO;

        for(CartItem item : items){

            Product product =
                    productRepository.findById(
                                    item.getProductId())
                            .orElseThrow();

            inventoryService.validateStock(
                    product.getId(),
                    item.getQuantity());

            total = total.add(
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantity()
                                    )
                            )
            );
        }

        Order order = new Order();

        order.setUserId(
                request.userId());

        order.setTotalAmount(total);

        order.setStatus(
                OrderStatus.CREATED);

        order.setCreatedAt(
                LocalDateTime.now());

        orderRepository.save(order);

        for(CartItem item : items){

            inventoryService.deductStock(
                    item.getProductId(),
                    item.getQuantity());
        }

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                total
        );
    }
}