package com.ouss.ecom.utils;

import com.ouss.ecom.dao.UserRepo;
import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.errors.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    private static UserRepo userRepo;

    public SecurityUtil(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public static AppUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new CustomException.NotFoundException("Email not Found"));
    }
}