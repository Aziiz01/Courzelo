package tn.esprit.pidev.services.Implementations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NiveauException extends RuntimeException {
    public NiveauException(String message) {
        super(message);
    }

}