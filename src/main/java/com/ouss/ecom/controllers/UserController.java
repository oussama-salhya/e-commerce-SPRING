package com.ouss.ecom.controllers;

import com.ouss.ecom.auth.AuthenticationService;
import com.ouss.ecom.auth.ChangePasswordRequest;
import com.ouss.ecom.dto.UserDTO;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "Get all users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of users",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "[\n" +
                                                    "    {\n" +
                                                    "        \"name\": \"test\",\n" +
                                                    "        \"email\": \"test@gmail.com\",\n" +
                                                    "        \"role\": \"USER\"\n" +
                                                    "    },\n" +
                                                    "    {\n" +
                                                    "        \"name\": \"test1\",\n" +
                                                    "        \"email\": \"test1@gmail.com\",\n" +
                                                    "        \"role\": \"USER\"\n" +
                                                    "    },\n" +
                                                    "]"
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
                                                    "    \"message\": try to acces as an admin\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Get current user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved current user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"name\": \"test\",\n" +
                                                    "    \"email\": \"test@gmail.com\",\n" +
                                                    "    \"role\": \"USER\"\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/showMe")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @Operation(
            summary = "Update user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"name\": \"oussama\",\n" +
                                            "    \"email\": \"oussama@gmail\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": try to acces as an admin\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    )
            }

    )
    @PatchMapping("/updateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO updatedUser, HttpServletResponse response) {
        UserDTO userDTO = userService.updateUser(updatedUser,response);

        return ResponseEntity.ok(userDTO);
    }

    @Operation(
            summary = "Update user password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user password",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\n" +
                                            "    \"currentPassword\" : \"karim123123\",\n" +
                                            "    \"newPassword\" : \"oussama1234\",\n" +
                                            "    \"confirmationPassword\" : \"oussama1234\"\n" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully updated user password",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "Success! Password Updated."
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
                                                    "    \"message\": try to acces as an admin\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"message\": \"the new Password and confirmation Password are not the same\n" +
                                                    "    \"status\": 401\n" +
                                                    "}"
                                    )
                            )
                    ),
            }

    )
    @PatchMapping("/updateUserPassword")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserPassword(@RequestBody ChangePasswordRequest req) {
        userService.changePassword(req);
        return ResponseEntity.ok("Success! Password Updated.");
    }

    @Operation(
            summary = "Get single user by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"name\": \"test\",\n" +
                                                    "    \"email\": \"test@gmail.com\",\n" +
                                                    "    \"role\": \"USER\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n" +
                                                    "    \"statusCode\": 404,\n" +
                                                    "    \"message\": \"No user with id : 20\"\n" +
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
                                                    "    \"message\": try to acces as an admin\n" +
                                                    "    \"status\": 403\n" +
                                                    "}"
                                    )
                            )
                    ),

            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getSingleUser(id));
    }
}