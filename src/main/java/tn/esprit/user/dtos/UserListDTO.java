package tn.esprit.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserListDTO {
    List<UserDTO> userResponse;
    int totalPages;
}
