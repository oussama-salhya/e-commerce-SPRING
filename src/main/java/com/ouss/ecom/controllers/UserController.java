package com.ouss.ecom.controllers;

import com.ouss.ecom.auth.AuthenticationService;
import com.ouss.ecom.auth.ChangePasswordRequest;
import com.ouss.ecom.dto.UserDTO;
import com.ouss.ecom.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/showMe")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO updatedUser, HttpServletResponse response) {
        UserDTO userDTO = userService.updateUser(updatedUser,response);

        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/updateUserPassword")
    public ResponseEntity<String> updateUserPassword(@RequestBody ChangePasswordRequest req) {
        userService.changePassword(req);
        return ResponseEntity.ok("Success! Password Updated.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getSingleUser(id));
    }
}