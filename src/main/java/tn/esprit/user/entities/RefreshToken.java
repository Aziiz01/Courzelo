package tn.esprit.user.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "refreshTokens")
@Builder
public class RefreshToken {
    @Id
    private String id;
    private String token;
    private Instant expiryDate;
    @DBRef
    private User user;
}
