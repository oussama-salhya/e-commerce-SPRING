package com.ouss.ecom.controllers;

import com.ouss.ecom.dto.ProductDTO;
import com.ouss.ecom.dto.ReviewDTO;
import com.ouss.ecom.entities.Product;
import com.ouss.ecom.services.ProductService;
import com.ouss.ecom.services.ReviewService;
import com.ouss.ecom.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(ProductDTO.toProductDTO(createdProduct), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(ProductDTO.toProductDTOList(products), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getSingleProduct(@PathVariable Long id) {
        Product product = productService.getSingleProduct(id);
        return new ResponseEntity<>(ProductDTO.toProductDTO(product), HttpStatus.OK);
    }
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getSingleProductReviews(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getSingleProductReviews(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product,id);
        return new ResponseEntity<>(ProductDTO.toProductDTO(updatedProduct), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Success! Product removed.", HttpStatus.OK);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        String imagePath = productService.uploadImage(image);
        return new ResponseEntity<>(imagePath, HttpStatus.OK);
    }
}