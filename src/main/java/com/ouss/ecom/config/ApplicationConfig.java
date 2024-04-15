package com.ouss.ecom.config;

import com.ouss.ecom.dao.UserRepo;
import com.ouss.ecom.entities.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {


  private final UserRepo repository;

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          AppUser appUser = repository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
//          String[] roles = appUser.getAuthorities().stream().map(r -> r.get()).toArray(String[]::new);
//          String role = appUser.getRole().getRole();
            String[] role = {appUser.getRole().getRole()};
          List<SimpleGrantedAuthority> authorities
                  = (List<SimpleGrantedAuthority>) appUser.getAuthorities();
          UserDetails userDetails =  User.withUsername(appUser.getEmail())
                  .password(appUser.getPassword())
                  .authorities(authorities)
                  .roles(role)
                  .build();
          return userDetails;
        }
    };
  }


  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
