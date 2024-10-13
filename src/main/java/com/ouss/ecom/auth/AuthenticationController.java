package com.ouss.ecom.auth;

import com.ouss.ecom.dto.UserDTO;
import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @Operation(
          summary = "Register a new user",
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "User object that needs to be registered",
                  required = true,
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(
                                  example = "{\n" +
                                          "\t\"name\": \"test\",\n" +
                                          "    \"email\": \"test@gmail.com\",\n" +
                                          "    \"password\": \"test@gmail.com\"  \n" +
                                          "}"
                          )
                  )
          ),
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "User registered successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(
                                          example = "User registered successfully"
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
                                          "    \"name\": \"test\",\n" +
                                          "    \"email\": \"test@gmail.com\",\n" +
                                          "    \"role\": \"USER\"\n" +
                                          "}"
                                  )
                          )
                  )
          }
  )
  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(
          @RequestBody @Valid AppUser user
  ) {
    return ResponseEntity.ok(service.register(user));
  }

  @Operation(
          summary = "Login a user",
          requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "User credentials for login",
                  required = true,
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(
                                  example = "{\n" +
                                          "\t\"email\": \"test@gmail.com\",\n" +
                                          "    \"password\": \"test@gmail.com\"\n" +
                                          "}"
                          )
                  )
          ),
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "User logged in successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(
                                          example = "{\n" +
                                                  "    \"name\": \"test\",\n" +
                                                  "    \"email\": \"test@gmail.com\",\n" +
                                                  "    \"role\": \"USER\"\n" +
                                                  "}"                                  )
                          )
                  ),
                  @ApiResponse(
                          responseCode = "403",
                          description = "Forbidden",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(
                                          example = "{\n" +
                                                  "    \"statusCode\": 403,\n" +
                                                  "    \"message\": \"Invalid credentials\"\n" +
                                                  "}"
                                  )
                          )
                  )
          }
  )
  @PostMapping("/login")
  public ResponseEntity<UserDTO> authenticate(
      @RequestBody AuthenticationRequest request,
        HttpServletResponse response
  ) {
    return ResponseEntity.ok(service.login(request,response));
  }
  @Operation(
          summary = "Logout a user",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "User logged out successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(
                                          example = "You have been logged out successfully."
                                  )
                          )
                  )
          }
  )
@PostMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    return "You have been logged out successfully.";
  }
//  @PostMapping("/refresh-token")
//  public void refreshToken(
//      HttpServletRequest request,
//      HttpServletResponse response
//  ) throws IOException {
//    service.refreshToken(request, response);
//  }

//  @GetMapping("/auth")
//    public ResponseEntity<AppUser> authentication() {
//        AppUser authentication = SecurityUtil.getAuthenticatedUser();
//    System.out.println("authentication = " + authentication );
//        return ResponseEntity.ok(authentication);
//  }


}
