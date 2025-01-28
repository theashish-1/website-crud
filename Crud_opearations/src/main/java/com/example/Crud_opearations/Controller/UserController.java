package com.example.Crud_opearations.Controller;

import com.example.Crud_opearations.DTO.AuthResponse;
import com.example.Crud_opearations.DTO.UserDTO;
import com.example.Crud_opearations.Entity.UserEntity;
import com.example.Crud_opearations.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody UserDTO userDTO) {

        UserEntity user = userService.findUserByEmail(userDTO.getEmail());

        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
            // Authentication successful; check admin status
            boolean isAdmin = user.isAdmin();


            return ResponseEntity.ok(new AuthResponse("User Login Successfully done", isAdmin));
        } else {
            // Authentication failed
            return ResponseEntity.status(401).body(new AuthResponse("Invalid email or password", false));
        }
    }

}
