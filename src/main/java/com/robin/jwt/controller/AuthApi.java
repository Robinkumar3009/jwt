package com.robin.jwt.controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.robin.jwt.model.AuthRequest;
import com.robin.jwt.model.AuthResponse;
import com.robin.jwt.model.User;
import com.robin.jwt.repository.UserRepository;
import com.robin.jwt.util.JwtTokenFilter;
import com.robin.jwt.util.JwtTokenUtil;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
 
 

 
@RestController
public class AuthApi {
    @Autowired AuthenticationManager authManager;
    @Autowired JwtTokenUtil jwtUtil;
    @Autowired private UserRepository repo;
    @Autowired JwtTokenFilter jwtTokenFilter;

    @Autowired BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @GetMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        try {
            // Create a new User instance
            User newUser = new User();

            // Check if a user with the provided email already exists
            Optional<User> registerUser = repo.findByEmail(user.getEmail());
            if (!registerUser.isEmpty()) {
                // If the email is already registered, return an error response
                Map<String, Object> data = new HashMap<>();
                data.put("msg", "This Email " + user.getEmail() + " is already registered");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(data);
            } else {
                // If the email is not registered, proceed with user registration
                newUser.setEmail(user.getEmail());
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));
                newUser.setUsername(user.getUsername());

                // Save the new user to the repository
                User savedUser = repo.save(newUser);

                // Return a success response with the saved user
                return ResponseEntity.ok().body(savedUser);
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during registration
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            // Attempt to authenticate the user using the provided email and password
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // If authentication is successful, get the authenticated user
            User user = (User) authentication.getPrincipal();

            // Generate an access token for the user
            String accessToken = jwtUtil.generateAccessToken(user);

            // Create a response containing user information and the access token
            Map<String, Object> data = new HashMap<>();
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
            data.put("userName", user.getEmail());
            data.put("accessToken", accessToken);

            // Return a successful response with user information and the access token
            return ResponseEntity.ok(data);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        	
        }
		
    }

    
    
    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        // Perform token validation here
        boolean isValidToken = jwtUtil.validateAccessToken(token);
     
        // Return true if the token is valid, otherwise return false
        return ResponseEntity.ok(isValidToken);
    }

//    private boolean validateTokenCheck(String token) {
//        // Your token validation logic goes here
//        // Check if the token is valid, hasn't expired, and is signed correctly
//        // You can use a library like JJwt to perform token validation
//    	boolean valid=jwtUtil.validateAccessToken(token);
//
//        // Example using JJwt (import io.jsonwebtoken.*;):
//        try {
//            Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws(token);
//            // If the above line doesn't throw an exception, the token is valid
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            // Token is not valid
//            return false;
//        }
//    }


}