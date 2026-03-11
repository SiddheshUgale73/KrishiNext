package com.krishinext.repositories;

import com.krishinext.models.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface SellerRepository extends MongoRepository<Seller, String> {
    Optional<Seller> findByEmail(String email);

    Optional<Seller> findByBrandName(String brandName);

    Optional<Seller> findByVerificationToken(String token);
}
