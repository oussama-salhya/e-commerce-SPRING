package com.ouss.ecom.dao;

import com.ouss.ecom.entities.Order;
import com.ouss.ecom.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}