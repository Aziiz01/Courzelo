package tn.esprit.user.entities;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Document(collection = "passwordResetTokens")
@Data
public class VerificationToken {
    private static final int EXPIRATION = 90;

    @Id
    private String id;

    private String token;
    private VerificationTokenType verificationTokenType;
    @DBRef
    private User user;

    private Instant expiryDate;

    public VerificationToken(String token, User user,VerificationTokenType verificationTokenType) {
        this.token = token;
        this.user = user;
        this.verificationTokenType = verificationTokenType;
        this.expiryDate = calculateExpiryDate();
    }

    private Instant calculateExpiryDate() {
        return Instant.now().plus(VerificationToken.EXPIRATION, ChronoUnit.MINUTES);
    }
}
