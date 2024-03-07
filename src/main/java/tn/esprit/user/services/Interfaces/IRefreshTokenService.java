package tn.esprit.user.services.Interfaces;

import tn.esprit.user.entities.RefreshToken;
import tn.esprit.user.entities.User;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(String email, long exp);

    RefreshToken findByToken(String token);

    void verifyExpiration(RefreshToken token);

    void deleteTokenByToken(String token);

    void deleteToken(RefreshToken token);

    void deleteAllUserToken(User user);

    void deleteAllUserTokenByEmail(String email);


}
