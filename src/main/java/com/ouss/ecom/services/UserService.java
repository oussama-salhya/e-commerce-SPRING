package com.ouss.ecom.services;

import com.ouss.ecom.auth.AuthenticationService;
import com.ouss.ecom.auth.ChangePasswordRequest;
import com.ouss.ecom.config.JwtService;
import com.ouss.ecom.dao.RoleRepo;
import com.ouss.ecom.dao.UserRepo;
import com.ouss.ecom.dto.UserDTO;
import com.ouss.ecom.entities.AppUser;
import com.ouss.ecom.errors.CustomException;
import com.ouss.ecom.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo repository;
    private final RoleRepo roleRepo;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, UserRepo repository, RoleRepo roleRepo, JwtService jwtService, AuthenticationService authenticationService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.roleRepo = roleRepo;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }


    public List<UserDTO> getAllUsers() {
        List<AppUser> users = userRepo.findAllByRole(roleRepo.findByRole("USER"));
        return users.stream().map(UserDTO::toUserDTO).collect(Collectors.toList());
    }

    public UserDTO getSingleUser(Long id) {
        AppUser user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException.NotFoundException("No user with id : " + id));
        return UserDTO.toUserDTO(user);
    }

    public UserDTO getCurrentUser() {
        AppUser currentUser = SecurityUtil.getAuthenticatedUser();
        return UserDTO.toUserDTO(currentUser);
    }

//    public UserDTO updateUser(UserDTO updatedUser, HttpServletResponse response) {
//        if (updatedUser.getEmail() == null || updatedUser.getName() == null) {
//            throw new CustomException.BadRequestException("Please provide all values");
//        }
//        AppUser currentUser = SecurityUtil.getAuthenticatedUser();
//        currentUser.setEmail(updatedUser.getEmail());
//        currentUser.setName(updatedUser.getName());
//        currentUser = userRepo.save(currentUser);
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                currentUser, null, currentUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        var jwtToken = jwtService.generateToken(currentUser);
//        response.addCookie(authenticationService.createCookie(jwtToken));
//        return UserDTO.toUserDTO(currentUser);
//    }
public UserDTO updateUser(UserDTO updatedUser, HttpServletResponse response) {
    if (updatedUser.getEmail() == null || updatedUser.getName() == null) {
        throw new CustomException.BadRequestException("Please provide all values");
    }
    AppUser currentUser = SecurityUtil.getAuthenticatedUser();
    currentUser.setEmail(updatedUser.getEmail());
    currentUser.setName(updatedUser.getName());
    currentUser = userRepo.save(currentUser);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            currentUser, null, currentUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    var jwtToken = jwtService.generateToken(currentUser);
    response.addCookie(authenticationService.createCookie(jwtToken));
    return UserDTO.toUserDTO(currentUser);
}
    public void changePassword(ChangePasswordRequest request) {

        AppUser user = SecurityUtil.getAuthenticatedUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException.UnauthorizedException("Invalid Credentials");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new CustomException.BadRequestException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }
}