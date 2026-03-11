package com.krishinext.repositories;

import com.krishinext.models.Order;
import com.krishinext.models.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findBySeller(Seller seller);
}
