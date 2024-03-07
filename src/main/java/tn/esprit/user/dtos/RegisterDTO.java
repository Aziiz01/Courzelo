package tn.esprit.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastname;
}
