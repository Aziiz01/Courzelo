package tn.esprit.user.services.Implementations;

import tn.esprit.user.dtos.UserDTO;
import tn.esprit.user.dtos.UserListDTO;
import tn.esprit.user.entities.Role;
import tn.esprit.user.entities.User;
import tn.esprit.user.exceptions.UserNotFoundException;
import tn.esprit.user.exceptions.UserRoleNotFoundException;
import tn.esprit.user.repositories.UserRepository;
import tn.esprit.user.security.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.user.services.Interfaces.IAdminService;

import java.util.List;

import static tn.esprit.user.services.Implementations.UserService.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService implements IAdminService {
    private final UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Response> toggleEnable(String userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + userID));
        boolean isEnabled = user.isEnabled();
        user.setEnabled(!isEnabled);
        userRepository.save(user);
        String message = isEnabled ? "User disabled!" : "User enabled!";
        log.info(message + " :" + userID);
        return ResponseEntity.ok().body(new Response(message));
    }

    public ResponseEntity<Response> toggleBan(String userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + userID));

        boolean isBanned = user.getBan() != null && user.getBan();
        user.setBan(!isBanned);
        userRepository.save(user);
        String message = isBanned ? "User unbanned!" : "User banned!";
        log.info(message + " :" + userID);
        return ResponseEntity.ok().body(new Response(message));
    }

    public ResponseEntity<Response> addRole(String role, String userID) {
        if (roleExist(role)) {
            User user = userRepository.findById(userID)
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + userID));
            if (user.getRoles().contains(Role.valueOf(role))) {
                return ResponseEntity
                        .badRequest()
                        .body(new Response("Role already assigned"));
            }
            user.getRoles().add(Role.valueOf(role));
            userRepository.save(user);
            return ResponseEntity.ok().body(new Response("Role assigned!"));
        }
        return ResponseEntity
                .badRequest()
                .body(new Response("Role Doesn't Exist"));
    }

    public ResponseEntity<Response> removeRole(String role, String userID) {

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + userID));
        if (!user.getRoles().contains(Role.valueOf(role))) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Role not assigned"));
        }
        user.getRoles().remove(Role.valueOf(role));
        userRepository.save(user);
        return ResponseEntity.ok().body(new Response("Role removed!"));
    }

    @Override
    public ResponseEntity<UserListDTO> getUsers(int page, int sizePerPage) {
        log.info("Getting all users");
        try {
            if (page < 0 || sizePerPage <= 0) {
                return ResponseEntity.badRequest().build();
            }

            Pageable pageable = PageRequest.of(page, sizePerPage);
            long totalItems = userRepository.count();
            log.info("Total users: {}", totalItems);

            int totalPages = (int) Math.ceil((double) totalItems / sizePerPage);
            log.info("Total pages: {}", totalPages);

            List<UserDTO> userDTOList = userRepository.findAll(pageable)
                    .stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .toList();

            log.info("Users in page {}: {}", page, userDTOList);
            UserListDTO userListDTO = new UserListDTO(userDTOList, totalPages);

            return ResponseEntity.ok().body(userListDTO);
        } catch (Exception e) {
            log.error("Error retrieving users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private Boolean roleExist(String role) {
        boolean roleExists = false;
        for (Role r : Role.values()) {
            if (r.equals(Role.valueOf(role))) {
                roleExists = true;
                break;
            }
        }
        if (!roleExists) {
            throw new UserRoleNotFoundException("Role not found");
        }
        return roleExists;
    }

    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userRepository.findAll()
                        .stream()
                        .map(user -> modelMapper.map(user, UserDTO.class))
                        .toList());
    }

}