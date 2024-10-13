package com.ouss.ecom.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ecom-roles")

public class Role {
    @Id
    private String role ;
    private Set<Authoritie> autorities;

    public enum Authoritie {
        READ,
        UPDATE,
        CREATE,
        DELETE,
    }
}
