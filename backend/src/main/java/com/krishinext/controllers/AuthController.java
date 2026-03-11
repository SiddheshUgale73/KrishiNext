package com.krishinext.controllers;

import com.krishinext.models.User;
import com.krishinext.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup/user")
    public ResponseEntity<User> signupUser(@RequestBody User user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/signup/seller")
    public ResponseEntity<Seller> signupSeller(@RequestBody Seller seller) {
        return ResponseEntity.ok(authService.registerSeller(seller));
    }

    @PostMapping("/login")
    public ResponseEntity<com.krishinext.dto.JwtResponse> login(
            @RequestBody com.krishinext.dto.LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verify(@PathVariable String token) {
        if (authService.verifyUser(token)) {
            return ResponseEntity.ok("Verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid or expired token");
    }
}
