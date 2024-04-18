package com.ouss.ecom.auth;

import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.entities.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<String> register(
          @RequestBody @Valid AppUser user
  ) {
    return ResponseEntity.ok(service.register(user));
  }
  @PostMapping("/login")
  public ResponseEntity<String> authenticate(
      @RequestBody AuthenticationRequest request,
        HttpServletResponse response
  ) {
    return ResponseEntity.ok(service.login(request,response));
  }

//  @PostMapping("/refresh-token")
//  public void refreshToken(
//      HttpServletRequest request,
//      HttpServletResponse response
//  ) throws IOException {
//    service.refreshToken(request, response);
//  }
  @GetMapping("/auth")
    public ResponseEntity<Authentication> authentication(Authentication authentication) {
    System.out.println("authentication = " + authentication );
        return ResponseEntity.ok(authentication);
  }


}
