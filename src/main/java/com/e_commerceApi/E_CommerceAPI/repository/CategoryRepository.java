package com.e_commerceApi.E_CommerceAPI.repository;

import com.e_commerceApi.E_CommerceAPI.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
