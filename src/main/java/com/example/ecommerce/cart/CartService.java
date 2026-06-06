package com.example.ecommerce.cart;

import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository itemRepository;

    private final ProductRepository productRepository;

    public void addProduct(
            Long cartId,
            AddToCartRequest request){

        Cart cart =
                cartRepository.findById(cartId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Cart Not Found"));

        Product product =
                productRepository.findById(
                                request.productId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product Not Found"));

        if(product.getStock() <
                request.quantity()){

            throw new BusinessException(
                    "Insufficient Stock");
        }

        CartItem item = new CartItem();

        item.setCart(cart);
        item.setProductId(product.getId());
        item.setQuantity(request.quantity());

        itemRepository.save(item);
    }
}