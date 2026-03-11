package com.krishinext.services;

import com.krishinext.models.User;
import com.krishinext.models.Seller;
import com.krishinext.repositories.UserRepository;
import com.krishinext.repositories.SellerRepository;
import com.krishinext.security.JwtUtils;
import com.krishinext.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationToken(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public Seller registerSeller(Seller seller) {
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        seller.setVerificationToken(UUID.randomUUID().toString());
        return sellerRepository.save(seller);
    }

    public com.krishinext.dto.JwtResponse login(com.krishinext.dto.LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        return new com.krishinext.dto.JwtResponse(jwt, userDetails.getUsername(), role);
    }

    public boolean verifyUser(String token) {
        return userRepository.findByVerificationToken(token)
                .map(user -> {
                    user.setVerified(true);
                    user.setVerificationToken(null);
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }
}
