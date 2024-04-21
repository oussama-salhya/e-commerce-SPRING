package com.ouss.ecom.controllers;

import com.ouss.ecom.dto.ReviewDTO;
import com.ouss.ecom.entities.Review;
import com.ouss.ecom.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody Review review, @RequestParam Long productId) {
        return ResponseEntity.ok(reviewService.createReview(review, productId));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getSingleReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getSingleReview(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Success! Review removed.");
    }

}