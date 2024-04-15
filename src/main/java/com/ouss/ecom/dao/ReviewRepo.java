package com.ouss.ecom.dao;

import com.ouss.ecom.entities.Product;
import com.ouss.ecom.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}