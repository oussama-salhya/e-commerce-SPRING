package com.ouss.ecom.entities;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotNull(message = "username shouldn't be null")
    @Size(min = 1, max = 15, message = "Name should be between 1 and 15")
    private String name;
}
