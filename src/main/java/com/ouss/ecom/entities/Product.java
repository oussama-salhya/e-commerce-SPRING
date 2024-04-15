package com.ouss.ecom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Builder.Default
    private String image = "/uploads/example.jpeg";

    @NotBlank(message = "Please provide product category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotBlank(message = "Please provide company")
    @Enumerated(EnumType.STRING)
    private Company company;

    @ElementCollection
    private List<String> colors;

    @Builder.Default
    private Boolean featured = false;

    @Builder.Default
    private Boolean freeShipping = false;

    @NotNull(message = "Please provide inventory")
    private Integer inventory = 15;

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

    public enum Category {
        OFFICE, KITCHEN, BEDROOM
    }

    public enum Company {
        IKEA, LIDDY, MARCOS
    }
}