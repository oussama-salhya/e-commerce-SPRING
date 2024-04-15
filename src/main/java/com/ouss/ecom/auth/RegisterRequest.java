package com.ouss.ecom.auth;

import com.ouss.ecom.entities.Role;
import com.ouss.ecom.entities.Role.Authoritie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String name;
  private String email;
  private String password;
  private Role role =  Role.builder()
          .role("USER")
          .autorities(Set.of(Authoritie.READ))
          .build();
  private Set<Authoritie> autorities = Set.of(
          Authoritie.READ);

}
