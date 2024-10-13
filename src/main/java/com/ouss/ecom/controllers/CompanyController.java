package com.ouss.ecom.controllers;

import com.ouss.ecom.entities.Company;
import com.ouss.ecom.services.CompanyService;
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
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @Operation(
            summary = "Create a company",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Company object that needs to be added",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"name\": \"ikea\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Company created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "Success! Company created."
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
                                                    "    \"message\": \"Company already exists\",\n" +
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
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        return ResponseEntity.ok(companyService.createCompany(company));
    }

    @Operation(
            summary = "Get all companies",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of companies",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "[\n" +
                                                    "    {\n" +
                                                    "        \"id\": 1,\n" +
                                                    "        \"name\": \"ikea\"\n" +
                                                    "    },\n" +
                                                    "    {\n" +
                                                    "        \"id\": 2,\n" +
                                                    "        \"name\": \"nike\"\n" +
                                                    "    }\n" +
                                                    "]"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @Operation(
            summary = "Get a single company",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Company found",
                            content = @Content(
                                    mediaType = "application/json",

                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"name\": \"ikea\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Company not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Company not found\",\n" +
                                                    "    \"status\": 404\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Company> getSingleCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getSingleCompany(id));
    }

    @Operation(
            summary = "Update a company",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Company object that needs to be updated",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"name\": \"Updated Company Name\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Company updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"name\": \"Updated Company Name\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Company not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Company not found\",\n" +
                                                    "    \"status\": 404\n" +
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
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return ResponseEntity.ok(companyService.updateCompany(id, company));
    }

    @Operation(
            summary = "Delete a company",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Company removed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "Success! Company removed."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Company not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"Company not found\",\n" +
                                                    "    \"status\": 404\n" +
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
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Success! Company removed.");
    }
}