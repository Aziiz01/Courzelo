package tn.esprit.user.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "DevicesMetadata")
public class DeviceMetadata {
    @Id
    private String id;
    @DBRef
    private User user;
    private String deviceDetails;
    private Instant lastLoggedIn;
}
