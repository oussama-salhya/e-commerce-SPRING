package com.ouss.ecom.dto;

import com.ouss.ecom.entities.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private String comment;
    private Integer rating;
    private UserDTO user;
    private Long productId;

    public static ReviewDTO toReviewDTO(Review review) {

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .user(UserDTO.toUserDTO(review.getUser()))
                .productId(review.getProduct().getId())
                .build();
        return reviewDTO;
    }
}