package com.example.jwtservice.controller;

import com.example.jwtservice.entity.User;
import com.example.jwtservice.service.UserService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody User user) throws NoSuchAlgorithmException {
        userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        return userService.login(user);
    }

    @GetMapping
    public List<User> getPosts(){
        return userService.getAllUsers();
    }



}
