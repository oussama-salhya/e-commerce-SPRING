package com.ouss.ecom.auth;

import com.ouss.ecom.dto.UserDTO;
import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<UserDTO> authenticate(
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

//  @GetMapping("/auth")
//    public ResponseEntity<AppUser> authentication() {
//        AppUser authentication = SecurityUtil.getAuthenticatedUser();
//    System.out.println("authentication = " + authentication );
//        return ResponseEntity.ok(authentication);
//  }


}
