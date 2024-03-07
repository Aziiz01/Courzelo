package tn.esprit.user.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
    private boolean rememberMe;

    public void setEmail(String email) {
        this.email = (email != null) ? email.toLowerCase() : null;
    }
}
