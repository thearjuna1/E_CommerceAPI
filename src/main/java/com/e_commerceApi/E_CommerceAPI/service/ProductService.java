package com.e_commerceApi.E_CommerceAPI.service;

import com.e_commerceApi.E_CommerceAPI.dto.ProductRequest;
import com.e_commerceApi.E_CommerceAPI.dto.ProductResponse;
import com.e_commerceApi.E_CommerceAPI.entity.Category;
import com.e_commerceApi.E_CommerceAPI.entity.Product;
import com.e_commerceApi.E_CommerceAPI.repository.CategoryRepository;
import com.e_commerceApi.E_CommerceAPI.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse create(ProductRequest productRequest) {
        Product product = new Product();

        product.setName(productRequest.getName());

        product.setDescription(productRequest.getDescription());

        product.setPrice(productRequest.getPrice());

        product.setStockQuantity(productRequest.getStockQuantity());

        Category category = categoryRepository.findById(productRequest.getCategoryId()).
                orElseThrow(() -> new EntityNotFoundException("This category does not exist"));

        product.setCategory(category);
        productRepository.save(product);

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());

        productResponse.setName(product.getName());

        productResponse.setDescription(productRequest.getDescription());

        productResponse.setPrice(productRequest.getPrice());

        productResponse.setStockQuantity(productRequest.getStockQuantity());

        productResponse.setCategoryName(category.getName());

        productResponse.setCategoryId(category.getId());

        return productResponse;
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Product does not exist"));

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setCategoryName(product.getCategory().getName());
        productResponse.setCategoryId(product.getCategory().getId());
        return productResponse;
    }

    public ProductResponse updateProductById(ProductRequest productRequest, Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product does not exist"));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());

        if (!product.getCategory().getId().equals(productRequest.getCategoryId())) {

            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category does not exist"));

            product.setCategory(category);
        }

        productRepository.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponse> getProductsByCategory( Long categoryId) {

//        Category category =  categoryRepository.findById(categoryId).orElseThrow(() ->
//                new EntityNotFoundException("Category does not exist"));

        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map( product ->  new ProductResponse(product.getId(), product.getName(),
                        product.getDescription(), product.getPrice() , product.getStockQuantity() ,
                        product.getCategory().getId(), product.getCategory().getName())).toList();



    }
}
