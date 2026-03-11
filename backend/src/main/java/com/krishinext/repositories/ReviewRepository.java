package com.krishinext.repositories;

import com.krishinext.models.Review;
import com.krishinext.models.Crop;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByCrop(Crop crop);
}
