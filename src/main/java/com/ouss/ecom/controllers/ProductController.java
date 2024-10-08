package com.ouss.ecom.controllers;

import com.ouss.ecom.dto.ProductDTO;
import com.ouss.ecom.dto.ReviewDTO;
import com.ouss.ecom.entities.Category;
import com.ouss.ecom.entities.Company;
import com.ouss.ecom.entities.Product;
import com.ouss.ecom.services.CategoryService;
import com.ouss.ecom.services.CompanyService;
import com.ouss.ecom.services.ProductService;
import com.ouss.ecom.services.ReviewService;
import com.ouss.ecom.specification.ProductSpecification;
import com.ouss.ecom.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private final CategoryService categoryService;
    private final CompanyService companyService;

    public ProductController(ProductService productService, ReviewService reviewService, CategoryService categoryService, CompanyService companyService) {

        this.productService = productService;
        this.reviewService = reviewService;
        this.categoryService = categoryService;
        this.companyService = companyService;
    }



    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createProduct(@Valid @RequestBody Product product){
        Product createdProduct = productService.createProduct(product);
//        return new ResponseEntity<>(ProductDTO.toProductDTO(createdProduct), HttpStatus.CREATED);
        return new ResponseEntity<>("Success! Product created.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) String color,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt.desc") String order) {

        Specification<Product> spec = Specification.where(null);

        if (search != null) {
            spec = spec.and(new ProductSpecification("name", "like", search));
        }

        if (featured != null) {
            spec = spec.and(new ProductSpecification("featured", "eq", featured));
        }

        if (category != null) {
            spec = spec.and(new ProductSpecification("category.name", "eq", category));
        }
        if (company != null) {
            spec = spec.and(new ProductSpecification("company.name", "eq", company));
        }

        if (minPrice != null) {
            spec = spec.and(new ProductSpecification("price", "ge", minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and(new ProductSpecification("price", "le", maxPrice));
        }

        if (minRating != null) {
            spec = spec.and(new ProductSpecification("averageRating", "ge", minRating));
        }

        if (maxRating != null) {
            spec = spec.and(new ProductSpecification("averageRating", "le", maxRating));
        }

        if (color != null) {
            spec = spec.and(new ProductSpecification("colors", "member", color));
        }

        List<Sort.Order> orders = new ArrayList<>();
        List<String> validProperties = Arrays.asList("name", "price", "averageRating");

            String[] parts = order.split("\\.");
            if (validProperties.contains(parts[0])) {
                if (parts.length >= 2) {
                    orders.add(parts[1].equalsIgnoreCase("asc") ? Sort.Order.asc(parts[0]) : Sort.Order.desc(parts[0]));
                } else {
                    // Assume a default sort direction (e.g., descending) if no direction is provided
                    orders.add(Sort.Order.desc(parts[0]));
                }
            }
        Pageable pageable = PageRequest.of(page == 0 ? 0 : page-1, size, Sort.by(orders));
        Page<Product> products = productService.getAllProducts(spec, pageable);
        Page<ProductDTO> productDTOs = products.map(ProductDTO::toProductDTO);

        // Fetch all categories and companies
        List<Category> categories = categoryService.getAllCategories();
        List<Company> companies = companyService.getAllCompanies();

        // Create a response object that includes products, categories, and companies
        Map<String, Object> response = new HashMap<>();
        response.put("products", productDTOs);
        response.put("categories", categories);
        response.put("companies", companies);

        return new ResponseEntity<>(response, HttpStatus.OK);
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product,id);
        return new ResponseEntity<>(ProductDTO.toProductDTO(updatedProduct), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Success! Product removed.", HttpStatus.OK);
    }


}