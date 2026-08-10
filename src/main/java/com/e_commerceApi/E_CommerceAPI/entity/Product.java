package com.e_commerceApi.E_CommerceAPI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer stockQuantity;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(nullable = false , name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItem = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItem = new ArrayList<>();
}
