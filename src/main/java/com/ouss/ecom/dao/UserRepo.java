package com.ouss.ecom.dao;

import com.ouss.ecom.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByEmail(String email);

}
