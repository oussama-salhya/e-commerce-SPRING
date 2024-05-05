package com.ouss.ecom.dto;

import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String name;
    private String email;
    private String role;

    public static UserDTO toUserDTO(AppUser user) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getRole())
                .build();
    }
}

