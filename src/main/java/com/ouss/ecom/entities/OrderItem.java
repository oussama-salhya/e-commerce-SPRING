package com.ouss.ecom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;

@Entity
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_items")
public class OrderItem {
    @EmbeddedId
    private OrderItemKey id;

    @NotBlank(message = "Please provide name")
    private String name;

    @NotBlank(message = "Please provide image")
    private String image;

    @NotNull(message = "Please provide price")
    private Double price;

    @NotNull(message = "Please provide amount")
    private Integer amount;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}