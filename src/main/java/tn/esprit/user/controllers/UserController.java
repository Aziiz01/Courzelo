package tn.esprit.user.controllers;

import tn.esprit.user.dtos.*;
import tn.esprit.user.security.Response;
import tn.esprit.user.services.Interfaces.IDeviceMetadataService;
import tn.esprit.user.services.Interfaces.IPhotoService;
import tn.esprit.user.services.Implementations.UserService;
import tn.esprit.user.security.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.security.Principal;
import java.util.function.Consumer;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPERADMIN')")
public class UserController {
    private final UserService userService;
    private final IPhotoService photoService;
    private final IDeviceMetadataService iDeviceMetadataService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/update/name")
    public ResponseEntity<Response> updateUserProfile(@Valid @RequestBody ProfileDTO user, Principal principal) {
        return userService.updateUserProfile(user, principal.getName());
    }

    @GetMapping("/getUser/{userID}")
    public UserDTO getUserByID(@PathVariable String userID) {
        return modelMapper.map(userService.getUserByID(userID), UserDTO.class);
    }

    @GetMapping("/myInfo")
    public JwtResponse getMyInfo(Principal principal) {

        return userService.getMyInfo(principal.getName());
    }

    @DeleteMapping("/deleteUser/{userID}")
    public ResponseEntity<Response> deleteUser(@PathVariable String userID) {
        return userService.deleteUser(userService.getUserByID(userID));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/update/password")
    public ResponseEntity<Response> changePassword(Principal principal, @Valid @RequestBody PasswordDTO passwordDTO) {
        return userService.changePassword(passwordDTO, principal.getName());
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/sendVerificationCode")
    public ResponseEntity<HttpStatus> sendVerificationCode(Principal principal) {
        return userService.sendVerificationCode(principal);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/email")
    public ResponseEntity<HttpStatus> changeEmail(@Valid @RequestBody UpdateEmailDTO updateEmailDTO, Principal principal) {
        return userService.updateEmail(updateEmailDTO, principal);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/photo")
    public ResponseEntity<HttpStatus> changePhoto(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        return userService.updatePhoto(file, principal);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getPhoto/{photoId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String photoId) {
        return photoService.getPhoto(photoId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/deleteAccount")
    public ResponseEntity<HttpStatus> deleteAccount(@Valid @RequestBody DeleteAccountDTO dto, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        return userService.deleteAccount(dto, principal, request, response);
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/device")
    public ResponseEntity<HttpStatus> deleteDevice(@RequestParam String id) {
        return iDeviceMetadataService.deleteDevice(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getDevices")
    public ResponseEntity<DeviceListDTO> getDevices(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "2") int sizePerPage,
                                                    Principal principal
    ) {
        return iDeviceMetadataService.getDevices(page, sizePerPage, principal);
    }

}