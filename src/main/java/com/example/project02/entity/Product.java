package com.example.project02.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, unique = true)
    private String code;

    // 전체 상품 개수
    @Column(columnDefinition = "integer default 0")
    private int quantity;

    // 전체 불량품 개수
    @Column(columnDefinition = "integer default 0")
    private int fquantity;

    // Add more fields and relationships as needed
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<ProductInBox> productsInBox;

}