package com.ouss.ecom.dto;

import com.ouss.ecom.entities.Category;
import com.ouss.ecom.entities.Company;
import com.ouss.ecom.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String image;
    private Category category;
    private Company company;
    private List<String> colors;
    private Boolean featured;
    private Boolean freeShipping;
    private Integer stock;
    private Double averageRating;
    private Integer numOfReviews;
    private UserDTO user;
    private List<ReviewDTO> reviews;



    public static ProductDTO toProductDTO(Product product) {
       ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .image(product.getImage())
                .category(product.getCategory())
                .company(product.getCompany())
                .colors(product.getColors())
                .featured(product.getFeatured())
                .freeShipping(product.getFreeShipping())
                .stock(product.getStock())
                .averageRating(product.getAverageRating())
                .numOfReviews(product.getNumOfReviews())
                .user(UserDTO.toUserDTO(product.getUser()))
                .build();
        return productDTO;
    }

    public static List<ProductDTO> toProductDTOList(List<Product> products) {
        return products.stream().map(ProductDTO::toProductDTO).collect(Collectors.toList());
    }
}