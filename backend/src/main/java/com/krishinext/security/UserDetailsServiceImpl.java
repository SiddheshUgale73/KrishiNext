package com.krishinext.security;

import com.krishinext.models.Seller;
import com.krishinext.models.User;
import com.krishinext.repositories.SellerRepository;
import com.krishinext.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Check users collection first
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return UserDetailsImpl.build(user.getEmail(), user.getPassword(), "ROLE_BUYER");
        }

        // Check sellers collection
        Seller seller = sellerRepository.findByEmail(email).orElse(null);
        if (seller != null) {
            return UserDetailsImpl.build(seller.getEmail(), seller.getPassword(), "ROLE_SELLER");
        }

        throw new UsernameNotFoundException("User Not Found with email: " + email);
    }
}
