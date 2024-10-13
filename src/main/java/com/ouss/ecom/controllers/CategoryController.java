package com.ouss.ecom.controllers;

import com.ouss.ecom.entities.Category;
import com.ouss.ecom.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Create a category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category object that needs to be added",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"name\": \"Sample Category\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "Success! Category created."
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
                                                    "    \"message\": \"Category already exists\",\n" +
                                                    "    \"status\": 400\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"try to acces as an admin\",\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @Operation(
            summary = "Get all categories",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of categories",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "[\n" +
                                                    "    {\n" +
                                                    "        \"id\": 1,\n" +
                                                    "        \"name\": \"office\"\n" +
                                                    "    },\n" +
                                                    "    {\n" +
                                                    "        \"id\": 2,\n" +
                                                    "        \"name\": \"sport\"\n" +
                                                    "    }\n" +
                                                    "]"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
            summary = "Get a single category",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"name\": \"office\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Category not found\",\n" +
                                                    "    \"status\": 404\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Category> getSingleCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getSingleCategory(id));
    }

    @Operation(
            summary = "Update a category",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category object that needs to be updated",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"name\": \"sport\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"id\": 3,\n" +
                                                    "    \"name\": \"sport\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"try to acces as an admin\",\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @Operation(
            summary = "Delete a category",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category removed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "Success! Category removed."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"try to acces as an admin\",\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Success! Category removed.");
    }
}