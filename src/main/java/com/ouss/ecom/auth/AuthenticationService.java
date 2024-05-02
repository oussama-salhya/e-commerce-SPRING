package com.ouss.ecom.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouss.ecom.config.JwtService;
import com.ouss.ecom.dao.RoleRepo;
import com.ouss.ecom.dao.UserRepo;
import com.ouss.ecom.dto.UserDTO;
import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.errors.CustomException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  public  int jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;
  @Value("${spring.profiles.active}")
  private String appEnv;

  private final UserRepo repository;
  private final RoleRepo roleRepositor;
  //  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public String register(AppUser user) {
    var existingUser = repository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      throw new CustomException.BadRequestException("Email already exists");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    if (user.getRole() == null) user.setRole(roleRepositor.findByRole("USER"));
    repository.save(user);
    return "User registered successfully";
  }

  public UserDTO login(AuthenticationRequest request , HttpServletResponse response) {
    AppUser user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new CustomException.UnauthorizedException("Invalid credentials"));
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new CustomException.UnauthenticatedException("Invalid credentials");
    }
    var jwtToken = jwtService.generateToken(user);
    response.addCookie(createCookie(jwtToken));
//    var refreshToken = jwtService.generateRefreshToken(user);
//    revokeAllUserTokens(user);
//    saveUserToken(user, jwtToken);
    return UserDTO.toUserDTO(user);
  }

  public Cookie createCookie(String value) {
    Cookie cookie = new Cookie("token", value);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(appEnv.equals("prod"));
    cookie.setMaxAge(jwtExpiration/1000);
    return cookie;
  }

//  private void saveUserToken(User user, String jwtToken) {
//    var token = Token.builder()
//        .user(user)
//        .token(jwtToken)
//        .tokenType(TokenType.BEARER)
//        .expired(false)
//        .revoked(false)
//        .build();
//    tokenRepository.save(token);
//  }
//
//  private void revokeAllUserTokens(User user) {
//    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//    if (validUserTokens.isEmpty())
//      return;
//    validUserTokens.forEach(token -> {
//      token.setExpired(true);
//      token.setRevoked(true);
//    });
//    tokenRepository.saveAll(validUserTokens);
//  }

//  public void refreshToken(
//          HttpServletRequest request,
//          HttpServletResponse response
//  ) throws IOException {
//    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//    final String refreshToken;
//    final String userEmail;
//    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//      return;
//    }
//    refreshToken = authHeader.substring(7);
//    userEmail = jwtService.extractUsername(refreshToken);
//    if (userEmail != null) {
//      var user = this.repository.findByEmail(userEmail)
//              .orElseThrow();
//      if (jwtService.isTokenValid(refreshToken, user)) {
//        var accessToken = jwtService.generateToken(user);
//        revokeAllUserTokens(user);
//        saveUserToken(user, accessToken);
//        var authResponse = AuthenticationResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//      }
//    }
//  }

}
