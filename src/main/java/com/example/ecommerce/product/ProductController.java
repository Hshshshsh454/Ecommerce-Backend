package com.example.ecommerce.product;


import com.example.ecommerce.product.ProductRequest;
import com.example.ecommerce.product.ProductResponse;
import com.example.ecommerce.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ProductResponse create(
            @Valid
            @RequestBody ProductRequest request){

        return service.create(request);
    }

    @GetMapping("/{id}")
    public ProductResponse get(
            @PathVariable Long id){

        return service.get(id);
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid
            @RequestBody ProductRequest request){

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id){

        service.delete(id);
    }

    @GetMapping("/search")
    public List<ProductResponse> search(
            @RequestParam String keyword){

        return service.search(keyword);
    }
}