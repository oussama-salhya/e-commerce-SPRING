package com.ouss.ecom.dao;

import com.ouss.ecom.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
public interface ProductRepo extends JpaRepository<Product, Long> {
        @Modifying
        @Transactional
        @Query("UPDATE Product p SET p.averageRating = (SELECT AVG(r.rating) FROM Review r WHERE r.product.id = p.id), p.numOfReviews = (SELECT COUNT(r) FROM Review r WHERE r.product.id = p.id) WHERE p.id = :productId")
        void updateProductRating(Long productId);
}