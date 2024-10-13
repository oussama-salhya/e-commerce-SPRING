package com.ouss.ecom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Entity
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ecom-order-items")
public class OrderItem {
    @EmbeddedId
    private OrderItemKey id;

    @NotBlank(message = "Please provide name")
    private String name;

//    @NotBlank(message = "Please provide image")
    private String image;

    @NotNull(message = "Please provide price")
    private Double price;

    @NotNull(message = "Please provide amount")
    private Integer amount;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Order order;
}