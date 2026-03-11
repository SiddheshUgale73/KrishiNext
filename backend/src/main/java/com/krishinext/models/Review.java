package com.krishinext.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import java.util.Date;

@Data
@Document(collection = "reviews")
@CompoundIndex(name = "review_unique_idx", def = "{'userId': 1, 'crop': 1}", unique = true)
public class Review {
    @Id
    private String id;

    @DocumentReference(collection = "users")
    private User user;

    @DocumentReference(collection = "crops")
    private Crop crop;

    private Integer stars;
    private String heading;
    private String description;
    private Date date = new Date();
}
