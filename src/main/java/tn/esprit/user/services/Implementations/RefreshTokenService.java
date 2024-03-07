package tn.esprit.user.services.Implementations;

import tn.esprit.user.entities.RefreshToken;
import tn.esprit.user.entities.User;
import tn.esprit.user.exceptions.RefreshTokenExpiredException;
import tn.esprit.user.repositories.RefreshTokenRepository;
import tn.esprit.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.user.services.Interfaces.IRefreshTokenService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenService implements IRefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(String email, long expiration) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findUserByEmail(email))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(expiration))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            log.info("verifyExpiration :Refresh Token removed from database");
            throw new RefreshTokenExpiredException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        log.info("verifyExpiration :Refresh token not expired");
    }

    @Override
    public void deleteTokenByToken(String token) {
        refreshTokenRepository.delete(refreshTokenRepository.findByToken(token));
    }

    @Override
    public void deleteToken(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    @Override
    public void deleteAllUserToken(User user) {
        List<RefreshToken> userTokens = refreshTokenRepository.findAllByUser(user);
        refreshTokenRepository.deleteAll(userTokens);
    }

    @Override
    public void deleteAllUserTokenByEmail(String email) {
        List<RefreshToken> userTokens = refreshTokenRepository.findAllByUser(userRepository.findUserByEmail(email));
        refreshTokenRepository.deleteAll(userTokens);

    }
}
