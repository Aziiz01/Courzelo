package tn.esprit.user.services.Interfaces;

import tn.esprit.user.dtos.UserDTO;
import tn.esprit.user.dtos.UserListDTO;
import tn.esprit.user.security.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAdminService {
    ResponseEntity<Response> toggleBan(String userID);

    ResponseEntity<Response> addRole(String role, String userID);

    ResponseEntity<Response> removeRole(String role, String userID);

    ResponseEntity<UserListDTO> getUsers(int page, int sizePerPage);

    ResponseEntity<Response> toggleEnable(String userID);
}

