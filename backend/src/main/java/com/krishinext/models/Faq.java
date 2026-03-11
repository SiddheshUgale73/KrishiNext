package com.krishinext.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import java.util.Date;

@Data
@Document(collection = "faqs")
@CompoundIndex(name = "faq_unique_idx", def = "{'crop': 1, 'sellerId': 1, 'userId': 1}", unique = true)
public class Faq {
    @Id
    private String id;
    private String question;
    private String answer;
    private boolean isAnswered = false;

    @DocumentReference(collection = "crops")
    private Crop crop;

    @DocumentReference(collection = "sellers")
    private Seller seller;

    @DocumentReference(collection = "users")
    private User user;

    private Date date = new Date();
}
