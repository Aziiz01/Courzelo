package tn.esprit.user.exceptions;

public class PhotoNotFoundException extends RuntimeException{
    public PhotoNotFoundException(String message) {
        super(message);
    }
}
