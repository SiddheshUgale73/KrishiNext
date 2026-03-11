package com.krishinext.repositories;

import com.krishinext.models.Crop;
import com.krishinext.models.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CropRepository extends MongoRepository<Crop, String> {
    Page<Crop> findByCategory(String category, Pageable pageable);

    List<Crop> findBySeller(Seller seller);
}
