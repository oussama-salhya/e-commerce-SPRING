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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    @Operation(
            summary = "Create a product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product object that needs to be added to the store",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"name\": \"Sample Product\",\n" +
                                            "    \"price\": 29.99,\n" +
                                            "    \"description\": \"This is a sample product description.\",\n" +
                                            "    // \"image\": \"path/to/image.jpg\",\n" +
                                            "    \"category\": {\n" +
                                            "        \"name\": \"office\"\n" +
                                            "    },\n" +
                                            "    \"company\": {\n" +
                                            "        \"name\": \"nike\"\n" +
                                            "    },\n" +
                                            "    \"colors\": [\"Red\", \"Blue\", \"Green\"],\n" +
                                            "    \"featured\": true,\n" +
                                            "    \"freeShipping\": false,\n" +
                                            "    \"stock\": 50,\n" +
                                            "    \"averageRating\": 4.5,\n" +
                                            "    \"numOfReviews\": 10,\n" +
                                            "    \"user\": {\n" +
                                            "        \"id\": 1\n" +
                                            "    }\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "Success! Product created."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Invalid input\",\n" +
                                                    "    \"status\": 400\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": you are not allowed to create a product\n\"," +
                                                    "  try to acces as an admin\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createProduct(@Valid @RequestBody Product product){
        Product createdProduct = productService.createProduct(product);
//        return new ResponseEntity<>(ProductDTO.toProductDTO(createdProduct), HttpStatus.CREATED);
        return new ResponseEntity<>("Success! Product created.", HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all products",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of products",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"companies\": [\n" +
                                                    "        {\n" +
                                                    "            \"id\": 1,\n" +
                                                    "            \"name\": \"ikea\"\n" +
                                                    "        },\n" +
                                                    "        {\n" +
                                                    "            \"id\": 2,\n" +
                                                    "            \"name\": \"nike\"\n" +
                                                    "        }\n" +
                                                    "    ],\n" +
                                                    "    \"categories\": [\n" +
                                                    "        {\n" +
                                                    "            \"id\": 1,\n" +
                                                    "            \"name\": \"office\"\n" +
                                                    "        },\n" +
                                                    "        {\n" +
                                                    "            \"id\": 2,\n" +
                                                    "            \"name\": \"sport\"\n" +
                                                    "        }\n" +
                                                    "    ],\n" +
                                                    "    \"products\": {\n" +
                                                    "        \"content\": [\n" +
                                                    "            {\n" +
                                                    "                \"id\": 1,\n" +
                                                    "                \"name\": \"product\",\n" +
                                                    "                \"price\": 907.78,\n" +
                                                    "                \"description\": \"omnis\",\n" +
                                                    "                \"image\": null,\n" +
                                                    "                \"category\": {\n" +
                                                    "                    \"id\": 1,\n" +
                                                    "                    \"name\": \"office\"\n" +
                                                    "                },\n" +
                                                    "                \"company\": {\n" +
                                                    "                    \"id\": 1,\n" +
                                                    "                    \"name\": \"ikea\"\n" +
                                                    "                },\n" +
                                                    "                \"colors\": [\n" +
                                                    "                    \"red\"\n" +
                                                    "                ],\n" +
                                                    "                \"featured\": false,\n" +
                                                    "                \"freeShipping\": false,\n" +
                                                    "                \"stock\": 238,\n" +
                                                    "                \"averageRating\": 0.0,\n" +
                                                    "                \"numOfReviews\": 0,\n" +
                                                    "                \"user\": {\n" +
                                                    "                    \"name\": \"admin\",\n" +
                                                    "                    \"email\": \"admin@gmail.com\",\n" +
                                                    "                    \"role\": \"ADMIN\"\n" +
                                                    "                },\n" +
                                                    "                \"reviews\": null\n" +
                                                    "            }\n" +
                                                    "        ],\n" +
                                                    "        \"pageable\": {\n" +
                                                    "            \"pageNumber\": 0,\n" +
                                                    "            \"pageSize\": 5,\n" +
                                                    "            \"sort\": {\n" +
                                                    "                \"empty\": false,\n" +
                                                    "                \"sorted\": true,\n" +
                                                    "                \"unsorted\": false\n" +
                                                    "            },\n" +
                                                    "            \"offset\": 0,\n" +
                                                    "            \"unpaged\": false,\n" +
                                                    "            \"paged\": true\n" +
                                                    "        },\n" +
                                                    "        \"last\": true,\n" +
                                                    "        \"totalPages\": 1,\n" +
                                                    "        \"totalElements\": 1,\n" +
                                                    "        \"size\": 5,\n" +
                                                    "        \"number\": 0,\n" +
                                                    "        \"sort\": {\n" +
                                                    "            \"empty\": false,\n" +
                                                    "            \"sorted\": true,\n" +
                                                    "            \"unsorted\": false\n" +
                                                    "        },\n" +
                                                    "        \"first\": true,\n" +
                                                    "        \"numberOfElements\": 1,\n" +
                                                    "        \"empty\": false\n" +
                                                    "    }\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
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


    @Operation(
            summary = "Get a single product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ProductDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Product not found\",\n" +
                                                    "    \"status\": 404\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getSingleProduct(@PathVariable Long id) {
        Product product = productService.getSingleProduct(id);
        return new ResponseEntity<>(ProductDTO.toProductDTO(product), HttpStatus.OK);
    }

   @Operation(
            summary = "Get all reviews of a product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of reviews",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "[\n" +
                                                    "    {\n" +
                                                    "        \"id\": 1,\n" +
                                                    "        \"comment\": \"I really enjoyed using this product. Highly recommended!\",\n" +
                                                    "        \"rating\": 1,\n" +
                                                    "        \"user\": {\n" +
                                                    "            \"name\": \"karim\",\n" +
                                                    "            \"email\": \"karim@gmail.com\",\n" +
                                                    "            \"role\": \"USER\"\n" +
                                                    "        },\n" +
                                                    "        \"productId\": 1\n" +
                                                    "    }\n" +
                                                    "]"
                                    )
                            )
                    )
            }
   )
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getSingleProductReviews(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getSingleProductReviews(id));
    }

    @Operation(
            summary = "Update a product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product object that needs to be updated",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "        \"id\": 1,\n" +
                                            "        \"name\": \"new Updated Product\",\n" +
                                            "        \"price\": 99.99,\n" +
                                            "        \"description\": \"This is a test product\",\n" +
                                            "        \"image\": \"/uploads/example.jpeg\",\n" +
                                            "        \"category\": {\n" +
                                            "            \"name\": \"office\"\n" +
                                            "        },\n" +
                                            "        \"company\": {\n" +
                                            "            \"name\": \"ikea\"\n" +
                                            "        },\n" +
                                            "        \"colors\": [\n" +
                                            "            \"Red\",\n" +
                                            "            \"Blue\"\n" +
                                            "        ],\n" +
                                            "        \"featured\": false,\n" +
                                            "        \"freeShipping\": false,\n" +
                                            "        \"inventory\": 10,\n" +
                                            "        \"averageRating\": 0.0,\n" +
                                            "        \"numOfReviews\": 0,\n" +
                                            "        \"user\": {\n" +
                                            "            \"id\": 1\n" +
                                            "        },\n" +
                                            "        \"reviews\": [],\n" +
                                            "           \n" +
                                            "    \"stock\": 50\n" +
                                            "    }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ProductDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Product not found\",\n" +
                                                    "    \"status\": 404\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product,id);
        return new ResponseEntity<>(ProductDTO.toProductDTO(updatedProduct), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete a product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product removed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "Success! Product removed."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Product not found\",\n" +
                                                    "    \"status\": 404\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Success! Product removed.", HttpStatus.OK);
    }


}