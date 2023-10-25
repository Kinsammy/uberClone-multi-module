package io.samtech.security.currentSecurity;

import io.samtech.repository.rdb.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;


import static io.samtech.constants.CommonConstants.SecurityString.AUTHORIZATION_HEADER;
import static io.samtech.constants.CommonConstants.SecurityString.BEARER_TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;


    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String  authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            return;
        }
        jwt = authHeader.substring(7);
        var savedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if (savedToken != null) {
            savedToken.setExpired(true);
            savedToken.setRevoked(true);
            tokenRepository.save(savedToken);
        }
    }
}
