package com.e_commerceApi.E_CommerceAPI.controller;

import com.e_commerceApi.E_CommerceAPI.dto.ProductRequest;
import com.e_commerceApi.E_CommerceAPI.dto.ProductResponse;
import com.e_commerceApi.E_CommerceAPI.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest ) {
        return new ResponseEntity<>(productService.create(productRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse>
    updateProductById(@PathVariable Long id, @RequestBody ProductRequest productRequest ) {
        return new  ResponseEntity<>(productService.
                updateProductById(productRequest , id) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
