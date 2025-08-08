package com.dsv.jobportal.controller;

import com.dsv.jobportal.dto.LoginRequest;
import com.dsv.jobportal.model.User;
import com.dsv.jobportal.model.UserPrincipal;
import com.dsv.jobportal.service.JobService;
import com.dsv.jobportal.service.JwtService;
import com.dsv.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JobService jobService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User register(@Validated @RequestBody User regUser){
        return userService.addUser(regUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginUser){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(),loginUser.getPassword()));
        if (authentication.isAuthenticated()){
            return ResponseEntity.ok(jwtService.generateToken(loginUser.getEmail()));
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }

    }

    @GetMapping("/hello")
    public String greet(){
        return "Hello " +jobService.getCurrentUser().getName();
    }

}
