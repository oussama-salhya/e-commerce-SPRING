package com.ouss.ecom.controllers;

import com.ouss.ecom.dto.ProductDTO;
import com.ouss.ecom.dto.ReviewDTO;
import com.ouss.ecom.entities.Review;
import com.ouss.ecom.errors.CustomError;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Operation(
            summary = "Create a review",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Review object that needs to be added to the store",
                    required = true,
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"rating\": 99.99,\n" +
                                                    "    \"title\": \"Great product\",\n" +
                                                    "    \"comment\": \"I really enjoyed using this product. Highly recommended!\"\n" +
                                                    "}"
                                    )
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Review created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"id\": 1,\n" +
                                                            "    \"comment\": \"I really enjoyed using this product. Highly recommended!\",\n" +
                                                            "    \"rating\": 1,\n" +
                                                            "    \"user\": {\n" +
                                                            "        \"name\": \"admin\",\n" +
                                                            "        \"email\": \"admin@gmail.com\",\n" +
                                                            "        \"role\": \"ADMIN\"\n" +
                                                            "    },\n" +
                                                            "    \"productId\": 1\n" +
                                                            "}"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"message\": \"Product not found\",\n" +
                                                            "    \"status\": 404\n" +
                                                            "}"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Already submitted review for this product",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"message\": \"Already submitted review for this product\",\n" +
                                                            "    \"status\": 400\n" +
                                                            "}"
                                            )
                                    )
                            }
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody Review review, @RequestParam Long productId) {
        return ResponseEntity.ok(reviewService.createReview(review, productId));
    }

    @Operation(
            summary = "Get all reviews",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of reviews",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = ReviewDTO.class
                                            )
                                    )
                            }
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @Operation(
            summary = "Get a single review",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Review found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"id\": 1,\n" +
                                                            "    \"comment\": \"I really enjoyed using this product. Highly recommended!\",\n" +
                                                            "    \"rating\": 1,\n" +
                                                            "    \"user\": {\n" +
                                                            "        \"name\": \"karim\",\n" +
                                                            "        \"email\": \"karim@gmail.com\",\n" +
                                                            "        \"role\": \"USER\"\n" +
                                                            "    },\n" +
                                                            "    \"productId\": 1\n" +
                                                            "}"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Review not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"message\": \"Review not found\",\n" +
                                                            "    \"status\": 404\n" +
                                                            "}"
                                            )

                                    )
                            }
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getSingleReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getSingleReview(id));
    }

    @Operation(
            summary = "Update a review",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Review object that needs to be updated",
                    required = true,
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                           example = "{\n" +
                                                   "    \"rating\": 4,\n" +
                                                   "    \"title\": \"Good product\",\n" +
                                                   "    \"comment\": \"I enjoyed using this product. It has some minor issues, but overall it's good.\"\n" +
                                                   "}"
                                    )
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Review updated"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Review not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"message\": \"Review not found\",\n" +
                                                            "    \"status\": 404\n" +
                                                            "}"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "You are not allowed to update this review",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"message\": \"You are not allowed to update this review\",\n" +
                                                            "    \"status\": 401\n" +
                                                            "}"
                                            )
                                    )
                            }
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    @Operation(
            summary = "Delete a review",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Review removed",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "Success! Review removed."
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Review not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"message\": \"Review not found\",\n" +
                                                            "    \"status\": 404\n" +
                                                            "}"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "You are not allowed to delete this review",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    example = "{\n" +
                                                            "    \"message\": \"You are not allowed to delete this review\",\n" +
                                                            "    \"status\": 401\n" +
                                                            "}"
                                            )
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Success! Review removed.");
    }

}