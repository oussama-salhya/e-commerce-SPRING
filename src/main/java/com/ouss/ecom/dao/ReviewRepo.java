package com.ouss.ecom.dao;

import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.entities.Product;
import com.ouss.ecom.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    Optional<Review> findByProductAndUser(Product product, AppUser user);

    List<Review> findByProduct(Product product);
}