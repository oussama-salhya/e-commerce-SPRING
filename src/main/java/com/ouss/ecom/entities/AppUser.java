package com.ouss.ecom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide name")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    private String name;

    @NotBlank(message = "Please provide email")
    @Email(message = "Please provide valid email")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Please provide password")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities
                = role.getAutorities().stream().map(r -> new SimpleGrantedAuthority(r.name()))
                .collect(Collectors.toList());
        return authorities;

    }
   
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

