package com.ouss.ecom.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide product name")
    @Size(max = 100, message = "Name can not be more than 100 characters")
    private String name;

    @NotNull(message = "Please provide product price")
    private Double price;

    @NotBlank(message = "Please provide product description")
    @Size(max = 1000, message = "Description can not be more than 1000 characters")
    private String description;

//    @Builder.Default
//    private String image = "/uploads/example.jpeg";
    private String image ;

    @ManyToOne
    @NotNull(message = "Please provide a valid product category")
    private Category category;

    @ManyToOne
    @NotNull(message = "Please provide a valid company")
    private Company company;


    @ElementCollection
    private List<String> colors;

    @Builder.Default
    private Boolean featured = false;

    @Builder.Default
    private Boolean freeShipping = false;

    @NotNull(message = "Please provide inventory")
    private Integer stock = 15;

    @Builder.Default
    private Double averageRating = 0.0;

    @Builder.Default
    private Integer numOfReviews = 0;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}