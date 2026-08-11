package com.e_commerceApi.E_CommerceAPI.service;

import com.e_commerceApi.E_CommerceAPI.dto.ProductResponse;
import com.e_commerceApi.E_CommerceAPI.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AllArgsConstructor
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetAllProductsByCategoryId(){

        when(productService.getProductsByCategory(1L))
                .thenReturn(List.of(new ProductResponse() , new  ProductResponse() , new ProductResponse() ));

        verify(productRepository.findByCategoryId(1L));



    }


}
