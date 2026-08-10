package com.e_commerceApi.E_CommerceAPI.service;

import com.e_commerceApi.E_CommerceAPI.dto.CategoryRequest;
import com.e_commerceApi.E_CommerceAPI.dto.CategoryResponse;
import com.e_commerceApi.E_CommerceAPI.entity.Category;
import com.e_commerceApi.E_CommerceAPI.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        Category category = new Category();

        category.setName(categoryRequest.getName());

        category.setDescription(categoryRequest.getDescription());

        categoryRepository.save(category);

        return new CategoryResponse(category.getId() , category.getDescription(), category.getName());
    }

    public CategoryResponse getCategoryById(Long id){

        Category category = categoryRepository.findById(id).orElseThrow(()->

                new EntityNotFoundException("Category not found"));

        return new CategoryResponse(category.getId() , category.getDescription(), category.getName());
    }


}
