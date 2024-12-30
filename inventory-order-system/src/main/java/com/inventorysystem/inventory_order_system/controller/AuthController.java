package com.inventorysystem.inventory_order_system.controller;

import com.inventorysystem.inventory_order_system.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> authRequest) {
        try {
            String username = authRequest.get("username");
            String password = authRequest.get("password");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());
            return Map.of("token", token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestParam String token) {
        if (!jwtUtils.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid Token"));
        }

        // Extract claims directly from the JwtUtils
        Claims claims = jwtUtils.extractAllClaims(token);
        return ResponseEntity.ok(Map.of(
                "message", "Token is valid",
                "username", claims.getSubject(),
                "roles", claims.get("roles")
        ));
    }

}
