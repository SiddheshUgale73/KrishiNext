package com.krishinext.repositories;

import com.krishinext.models.Payment;
import com.krishinext.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByOrder(Order order);

    Optional<Payment> findByTransactionId(String transactionId);
}
