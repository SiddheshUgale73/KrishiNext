package com.krishinext.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import java.util.Date;

@Data
@Document(collection = "crops")
public class Crop {
    @Id
    private String id;
    private String image;
    private String brand;
    private String name;

    @Indexed
    private String category;

    private String description;
    private Double pricePerUnit;
    private String measuringUnit;
    private Integer minimumOrderQuantity;
    private Integer deliveryRadius;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    private Integer quantity;
    private String shelfLife;

    @DocumentReference(collection = "sellers")
    private Seller seller;

    private Date date = new Date();
}
