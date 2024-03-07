package tn.esprit.user.dtos;

import lombok.Data;
@Data
public class UpdateEmailDTO {
    private String email;
    private int code;

    public void setEmail(String email) {
        this.email = (email != null) ? email.toLowerCase() : null;
    }

}
