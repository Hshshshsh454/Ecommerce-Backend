package com.example.ecommerce.product;


import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.product.ProductRequest;
import com.example.ecommerce.product.ProductResponse;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductResponse create(
            ProductRequest request){

        Product product =
                Product.builder()
                        .name(request.name())
                        .price(request.price())
                        .stock(request.stock())
                        .build();

        return toResponse(
                repository.save(product));
    }

    public ProductResponse get(Long id){

        return toResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found")));
    }

    public ProductResponse update(
            Long id,
            ProductRequest request){

        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());

        return toResponse(
                repository.save(product));
    }

    public void delete(Long id){

        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        repository.delete(product);
    }

    public List<ProductResponse> search(
            String keyword){

        return repository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponse toResponse(
            Product product){

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock());
    }
}