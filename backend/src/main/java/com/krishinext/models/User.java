package com.krishinext.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.Date;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    
    @Indexed(unique = true)
    private Long contact;
    
    @Indexed(unique = true)
    private String email;
    
    private String password;
    private boolean isVerified = false;
    private String verificationToken;
    private Date verificationTokenExpiry;
    private Date date = new Date();
}
