package com.krishinext.repositories;

import com.krishinext.models.Faq;
import com.krishinext.models.Crop;
import com.krishinext.models.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FaqRepository extends MongoRepository<Faq, String> {
    List<Faq> findByCrop(Crop crop);

    List<Faq> findBySeller(Seller seller);
}
