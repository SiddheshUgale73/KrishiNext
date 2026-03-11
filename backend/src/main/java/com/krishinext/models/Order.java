package com.krishinext.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import java.util.Date;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;

    @DocumentReference(collection = "crops")
    private Crop crop;

    @DocumentReference(collection = "users")
    private User user;

    private Integer orderQty;

    private GeoJsonPoint orderLocation;

    @DocumentReference(collection = "sellers")
    private Seller seller;

    private Date date = new Date();
}
