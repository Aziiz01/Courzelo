package tn.esprit.user.exceptions;

public class PasswordResetTokenNotFoundException extends RuntimeException {
    public PasswordResetTokenNotFoundException(String message) {
        super(message);
    }
}
