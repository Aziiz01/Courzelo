package tn.esprit.user.dtos;

import tn.esprit.user.entities.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class UserDTO {
    private String id;
    private String email;
    private String name;
    private String lastName;
    private List<Role> roles;
    private boolean ban;
    private boolean enabled;
    private String nb_certificat;
    private String score;
}
