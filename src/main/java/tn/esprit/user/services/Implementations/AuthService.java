package tn.esprit.user.services.Implementations;

import tn.esprit.user.dtos.DeviceResDTO;
import tn.esprit.user.dtos.LoginDTO;
import tn.esprit.user.dtos.RecoverPasswordDTO;
import tn.esprit.user.entities.*;
import tn.esprit.user.exceptions.*;
import tn.esprit.user.repositories.VerificationTokenRepository;
import tn.esprit.user.repositories.UserRepository;
import tn.esprit.user.security.JwtResponse;
import tn.esprit.user.security.Response;
import tn.esprit.user.security.jwt.JWTUtils;
import tn.esprit.user.services.Interfaces.IAuthService;
import tn.esprit.user.services.Interfaces.IDeviceMetadataService;
import tn.esprit.user.services.Interfaces.IRefreshTokenService;
import tn.esprit.user.utils.CookieUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final IRefreshTokenService iRefreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JWTUtils jwtUtils;
    private final CookieUtil cookieUtil;
    private final EmailService emailService;
    private final IDeviceMetadataService iDeviceMetadataService;
    @Value("${Security.app.jwtExpirationMs}")
    private long jwtExpirationMs;
    @Value("${Security.app.refreshExpirationMs}")
    private long refreshExpirationMs;
    @Value("${Security.app.refreshRememberMeExpirationMs}")
    private long refreshRememberMeExpirationMs;

    public ResponseEntity<?> loginUser(LoginDTO loginDTO, @NonNull HttpServletResponse response, String userAgent) {
        log.info("Starting Logging in...");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            User checkUser = userRepository.findUserByEmail(loginDTO.getEmail());
            if (!iDeviceMetadataService.isNewDevice(userAgent, checkUser)) {
                log.info("Finished Logging in...");
                return authenticateUser(authentication, response, loginDTO);
            } else {
                try {
                    Random random = new Random();
                    int verificationCode = random.nextInt(9000) + 1000;
                    VerificationToken verificationToken = new VerificationToken(
                            String.valueOf(verificationCode),
                            checkUser,
                            VerificationTokenType.DEVICE_VERIFICATION
                    );
                    verificationTokenRepository.save(verificationToken);
                    emailService.sendVerificationCode(checkUser, verificationToken);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                log.info("Finished Logging in...");
                return ResponseEntity.status(HttpStatus.OK).body(new DeviceResDTO(true));
            }
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response("Please verify your email"));
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response("Your account has been banned"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Incorrect email or password"));
        }
    }

    private ResponseEntity<?> authenticateUser(Authentication authentication, @NonNull HttpServletResponse response, LoginDTO loginDTO) {
        log.info("Starting Authentication...");
        log.info("Email :" + loginDTO.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateJwtToken(authentication.getName());
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(accessToken, jwtExpirationMs).toString());

        User userDetails = (User) authentication.getPrincipal();
        RefreshToken refreshToken = null;

        if (loginDTO.isRememberMe()) {
            refreshToken = iRefreshTokenService.createRefreshToken(loginDTO.getEmail(), refreshRememberMeExpirationMs);
            userDetails.setRememberMe(true);
            log.info("RememberMe : On");
        } else {
            refreshToken = iRefreshTokenService.createRefreshToken(loginDTO.getEmail(), refreshExpirationMs);
            userDetails.setRememberMe(false);
            log.info("RememberMe : Off");
        }

        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie(refreshToken.getToken(), loginDTO.isRememberMe() ? refreshRememberMeExpirationMs : refreshExpirationMs).toString());

        userRepository.save(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        log.info("Authentication finished!");

        JwtResponse jwtResponse = new JwtResponse(
                userDetails.getEmail(),
                userDetails.getName(),
                userDetails.getLastName(),
                roles,
                userDetails.getPhoto() != null ? userDetails.getPhoto().getId() : null
        );

        return ResponseEntity.ok(jwtResponse);
    }


    public ResponseEntity<?> confirmDevice(String userAgent, @NonNull HttpServletResponse response, LoginDTO loginDTO, Integer code) {
        log.info("Started Confirming Device...");
        User user = userRepository.findUserByEmail(loginDTO.getEmail());
        VerificationToken verificationToken = verificationTokenRepository.findByToken(String.valueOf(code));
        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Wrong Verification Code"));
        }
        if (Objects.equals(verificationToken.getUser().getId(), user.getId())) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
                ResponseEntity<?> response1 = authenticateUser(authentication, response, loginDTO);
                User checkUser = userRepository.findUserByEmail(loginDTO.getEmail());
                iDeviceMetadataService.saveDeviceDetails(userAgent, checkUser);
                log.info("Finished Confirming Device...");
                return response1;
            } catch (DisabledException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response("Please verify your email"));
            } catch (LockedException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response("Your account has been banned"));
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Incorrect email or password"));
            }
        }
        log.info("Started Confirming Device...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Wrong Verification Code"));
    }


    public ResponseEntity<Response> verifyAccount(String code) {
        log.info("Started Verifying...");
        log.info(code);
        VerificationToken verificationToken = verificationTokenRepository.findByToken(code);
        if (verificationToken == null) {
            throw new PasswordResetTokenNotFoundException("PasswordResetToken Not Found " + code);
        }
        if (!verificationToken.getVerificationTokenType().equals(VerificationTokenType.EMAIL_VERIFICATION)) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Token not valid"));
        }
        if (verificationToken.getExpiryDate().isBefore(Instant.now())) {
            throw new PasswordResetTokenExpiredException("PasswordResetToken Expired " + verificationToken.getExpiryDate());
        }
        User user = userRepository.findUserById(verificationToken.getUser().getId());
        if (user != null) {
            log.info(user.getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            log.info("Finished Verifying...");
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Account Verified"));
        }
        log.info("Finished Verifying...");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("Verification Failed"));
    }

    @Override
    public ResponseEntity<Boolean> isAuthenticated(@NonNull HttpServletRequest request) {
        String accessToken = cookieUtil.getAccessTokenFromCookies(request);
        if (accessToken != null && jwtUtils.validateJwtToken(accessToken)) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.ok().body(false);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<List<Role>> getRole(Principal principal) {
        User user = userRepository.findUserByEmail(principal.getName());
        if (user != null) {
            return ResponseEntity.ok().body(user.getRoles());
        }
        return null;
    }

    @Override
    public ResponseEntity<Response> forgotPassword(String email) throws MessagingException, UnsupportedEncodingException {
        log.info("Forgot password started....");
        log.info("Finding user...");
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User " + email + " not found");
        }
        log.info("User found");
        VerificationToken verificationToken = new VerificationToken(UUID.randomUUID().toString(), user, VerificationTokenType.FORGOT_PASSWORD);
        verificationTokenRepository.save(verificationToken);
        emailService.sendPasswordChangeEmail(user, verificationToken);
        return ResponseEntity
                .ok()
                .body(new Response("Email sent to " + email));
    }

    @Override
    public ResponseEntity<Response> recoverPassword(String token, RecoverPasswordDTO passwordDTO) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new PasswordResetTokenNotFoundException("PasswordResetToken Not Found " + token);
        }
        if (!verificationToken.getVerificationTokenType().equals(VerificationTokenType.FORGOT_PASSWORD)) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Token not valid"));
        }
        if (verificationToken.getExpiryDate().isBefore(Instant.now())) {
            throw new PasswordResetTokenExpiredException("PasswordResetToken Expired " + verificationToken.getExpiryDate());
        }
        User user = userRepository.findUserById(verificationToken.getUser().getId());
        if (user == null) {
            throw new UserNotFoundException("User Not Found with id " + verificationToken.getUser().getId());
        }
        user.setPassword(encoder.encode(passwordDTO.getPassword()));
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return ResponseEntity
                .ok()
                .body(new Response("Password Changed!"));
    }

    public ResponseEntity<Response> saveUser(User user, String userAgent) {
        log.info("Started Signing up...");
        if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Email is already in use!"));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRoles().add(Role.STUDENT);
        user.setEnabled(false);
        user.setBan(false);
        userRepository.save(user);
        String randomCode = RandomString.make(64);
        VerificationToken verificationToken = new VerificationToken(
                randomCode,
                user,
                VerificationTokenType.EMAIL_VERIFICATION
        );
        verificationTokenRepository.save(verificationToken);
        iDeviceMetadataService.saveDeviceDetails(userAgent, user);
        try {
            emailService.sendVerificationEmail(user, verificationToken);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Finished Signing up...");
        return ResponseEntity
                .ok()
                .body(new Response("Account Created!"));
    }

    public void logout(@NonNull HttpServletResponse response) {
        log.info("Logout :Logging out...");
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie("accessToken", 0L).toString());
        log.info("Logout: Access Token removed");
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie("refreshToken", 0L).toString());
        log.info("Logout :Refresh Token removed");
        SecurityContextHolder.clearContext();
        log.info("Logout :Security context cleared!");
        log.info("Logout :Logout Finished!");
    }

    public void refreshToken(@NonNull HttpServletResponse response, String email) {
        log.info("refreshToken :Refreshing Token...");
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(jwtUtils.generateJwtToken(email), jwtExpirationMs).toString());
        log.info("refreshToken :Access token created!");
        log.info("refreshToken :Refreshing token DONE!");
    }
}
