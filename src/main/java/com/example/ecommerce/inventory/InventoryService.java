package com.example.ecommerce.inventory;


import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final ProductRepository productRepository;

    public void validateStock(
            Long productId,
            Integer quantity){

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product Not Found"));

        if(product.getStock() < quantity){

            throw new BusinessException(
                    "Out Of Stock");
        }
    }

    public void deductStock(
            Long productId,
            Integer quantity){

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product Not Found"));

        product.setStock(
                product.getStock()
                        - quantity);

        productRepository.save(product);
    }
}

