package com.ouss.ecom.config;

//import ma.fsts.springboot3withjwt.security.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
//  private final TokenRepository tokenRepository;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    String servletPath = request.getServletPath();
    if (servletPath.contains("/auth") || servletPath.contains("/swagger") || servletPath.contains("/v2/api-docs") || servletPath.contains("/v3/api-docs") || servletPath.contains("/swagger-resources") || servletPath.contains("/configuration/ui") || servletPath.contains("/configuration/security") || servletPath.contains("/webjars") || servletPath.contains("/api-docs")) {
      filterChain.doFilter(request, response);
      return;
    }
//    final String authHeader = request.getHeader("Authorization");
    String jwt = null;
    String userEmail = null;
    // Get cookies from the request
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("token")) {
          jwt = cookie.getValue();
          userEmail = jwtService.extractUsername(jwt);
          break;
        }
      }
    }
    System.out.println("jwt = " + jwt);
//    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//    jwt = authHeader.substring(7);
//    userEmail = jwtService.extractUsername(jwt);
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//      var isTokenValid = tokenRepository.findByToken(jwt)
//          .map(t -> !t.isExpired() && !t.isRevoked())
//          .orElse(false);
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    } else {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
      return;
    }
    filterChain.doFilter(request, response);
  }
}