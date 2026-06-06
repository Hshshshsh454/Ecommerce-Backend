package com.example.ecommerce.payment;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.order.Order;
import com.example.ecommerce.order.OrderRepository;
import com.example.ecommerce.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    public Payment pay(
            PaymentRequest request){

        Order order =
                orderRepository.findById(
                                request.orderId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Order Not Found"));

        Payment payment =
                new Payment();

        payment.setOrderId(
                order.getId());

        payment.setAmount(
                order.getTotalAmount());

        payment.setMethod(
                request.method());

        payment.setStatus(
                PaymentStatus.SUCCESS);

        order.setStatus(
                OrderStatus.PAID);

        return paymentRepository.save(
                payment);
    }
}