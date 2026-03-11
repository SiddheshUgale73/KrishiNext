package com.krishinext.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import java.util.Date;
import java.util.Map;

@Data
@Document(collection = "payments")
public class Payment {
    @Id
    private String id;

    @DocumentReference(collection = "orders")
    private Order order;

    @DocumentReference(collection = "users")
    private User user;

    private String paymentMethod; // enum: Credit Card, PayPal, Bank Transfer, Stripe, Razorpay
    private String transactionId;
    private String paymentStatus = "Pending"; // enum: Pending, Completed, Failed, Refunded
    private Double amountPaid;
    private Date paymentDate = new Date();
    private Map<String, Object> paymentDetails;
}
