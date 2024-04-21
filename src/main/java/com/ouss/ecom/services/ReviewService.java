package com.ouss.ecom.services;

import com.ouss.ecom.dao.ReviewRepo;
import com.ouss.ecom.dao.ProductRepo;
import com.ouss.ecom.dto.ReviewDTO;
import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.entities.Review;
import com.ouss.ecom.entities.Product;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private ProductRepo productRepo;

    public ReviewDTO createReview(Review review, Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product with id " + productId));

        AppUser user = SecurityUtil.getAuthenticatedUser();

        Optional<Review> alreadySubmitted = reviewRepo.findByProductAndUser(product, user);
        if (alreadySubmitted.isPresent()) {
            throw new CustomException.BadRequestException("Already submitted review for this product");
        }

        review.setUser(user);
        review.setProduct(product);
        productRepo.updateProductRating(productId);
        return ReviewDTO.toReviewDTO(reviewRepo.save(review));
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepo.findAll().stream().map(ReviewDTO::toReviewDTO).toList();
    }

    public ReviewDTO getSingleReview(Long id) {
        Review review = reviewRepo.findById(id).orElseThrow(
                () -> new CustomException.NotFoundException("No review with id " + id));
        return ReviewDTO.toReviewDTO(review);
    }

    public ReviewDTO updateReview(Long id, Review updatedReview) {
        Review review = reviewRepo.findById(id).orElseThrow(
                () -> new CustomException.NotFoundException("No review with id " + id)
        );

        review.setTitle(updatedReview.getTitle());
        review.setComment(updatedReview.getComment());
        review.setRating(updatedReview.getRating());
        productRepo.updateProductRating(review.getProduct().getId());

        return ReviewDTO.toReviewDTO(reviewRepo.save(review));
    }

    public void deleteReview(Long id) {
        Review review = reviewRepo.findById(id).orElseThrow(
                () -> new CustomException.NotFoundException("No review with id " + id)
        );

        // checkPermissions(userId, review.get().getUser());

        productRepo.updateProductRating(review.getProduct().getId());

        reviewRepo.delete(review);
    }

    public List<ReviewDTO> getSingleProductReviews(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product with id " + productId));
        return reviewRepo.findByProduct(product).stream().map(ReviewDTO::toReviewDTO).toList();
    }
}