package tn.esprit.user.security.jwt;

import tn.esprit.user.entities.RefreshToken;
import tn.esprit.user.services.Implementations.AuthService;
import tn.esprit.user.services.Interfaces.IRefreshTokenService;
import tn.esprit.user.services.Implementations.UserService;
import tn.esprit.user.utils.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    private static final Logger jwtLogger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private IRefreshTokenService iRefreshTokenService;
    @Autowired
    private AuthService authService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        List<String> excludedEndpoints = Arrays.asList(
                "/api/v1/auth/signing",
                "/api/v1/auth/signup",
                "/api/v1/auth/logout",
                "/api/v1/auth/refreshToken",
                "/api/v1/auth/verify",
                "/api/v1/auth/confirmDevice/",
                "/api/v1/auth/recover-password"
        );

        String requestUri = request.getRequestURI();
        log.info("Request URI: " + requestUri);

        if (isExcludedEndpoint(requestUri, excludedEndpoints)) {
            log.info("Excluded endpoint: " + requestUri);
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = cookieUtil.getAccessTokenFromCookies(request);
        String refreshToken = cookieUtil.getRefreshTokenFromCookies(request);
        log.info("Tokens retrieved");

        try {
            if (accessToken != null && jwtUtils.validateJwtToken(accessToken)) {
                String email = jwtUtils.getEmailFromJwtToken(accessToken);
                if (userDetailsService.ValidUser(email)) {
                    UserDetails userDetails = userDetailsService.loadUserByEmail(email);
                    setAuthenticationInSecurityContext(request, userDetails);
                }
            } else if (refreshToken != null) {
                RefreshToken token = iRefreshTokenService.findByToken(refreshToken);
                iRefreshTokenService.verifyExpiration(token);
                authService.refreshToken(response, token.getUser().getEmail());
                if (userDetailsService.ValidUser(token.getUser().getEmail())) {
                    log.info("Valid refresh token for user: " + token.getUser().getEmail());
                    UserDetails userDetails = userDetailsService.loadUserByEmail(token.getUser().getEmail());
                    setAuthenticationInSecurityContext(request, userDetails);
                }
            }
        } catch (Exception e) {
            jwtLogger.error("Error during authentication: {}", e.getMessage());
        }
        log.info("Request Filter almost completed");
        filterChain.doFilter(request, response);
        log.info("Request Filter Finished");
    }

    private boolean isExcludedEndpoint(String requestUri, List<String> excludedEndpoints) {
        return excludedEndpoints.stream().anyMatch(requestUri::startsWith);
    }


    private void setAuthenticationInSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}