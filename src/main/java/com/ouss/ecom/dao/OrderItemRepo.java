package com.ouss.ecom.dao;

import com.ouss.ecom.entities.Order;
import com.ouss.ecom.entities.OrderItem;
import com.ouss.ecom.entities.OrderItemKey;
import com.ouss.ecom.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface OrderItemRepo extends JpaRepository<OrderItem, OrderItemKey> {
    OrderItem findByOrderAndProduct(Order savedOrder, Product dbProduct);
}