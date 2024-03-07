package tn.esprit.user.controllers;

import jakarta.mail.MessagingException;
import tn.esprit.user.dtos.LoginDTO;
import tn.esprit.user.dtos.RecoverPasswordDTO;
import tn.esprit.user.dtos.RegisterDTO;
import tn.esprit.user.entities.Role;
import tn.esprit.user.entities.User;
import tn.esprit.user.security.Response;
import tn.esprit.user.services.Interfaces.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {

    private final IAuthService iAuthService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/confirmDevice/{code}")
    public ResponseEntity<?> confirmDevice(@Valid @RequestBody LoginDTO loginDTO, @PathVariable Integer code, HttpServletResponse response, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return iAuthService.confirmDevice(userAgent, response, loginDTO, code);
    }

    @PostMapping("/signing")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response, @RequestHeader(value = "User-Agent") String userAgent) {
        return iAuthService.loginUser(loginDTO, response, userAgent);
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@Valid @RequestBody RegisterDTO user, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return iAuthService.saveUser(modelMapper.map(user, User.class), userAgent);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        iAuthService.logout(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<Response> verifyAccount(@RequestParam("code") String code) {
        return iAuthService.verifyAccount(code);
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest request) {
        return iAuthService.isAuthenticated(request);
    }

    @GetMapping("/getRole")
    public ResponseEntity<List<Role>> getRole(Principal principal) {
        return iAuthService.getRole(principal);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        return iAuthService.forgotPassword(email);
    }

    @PostMapping("/recover-password")
    public ResponseEntity<Response> recoverPassword(@RequestParam("token") String token, @RequestBody RecoverPasswordDTO passwordDTO) {
        return iAuthService.recoverPassword(token, passwordDTO);
    }
}