package com.ouss.ecom.dao;

import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public interface UserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    List<AppUser> findAllByRole(Role role);
}
