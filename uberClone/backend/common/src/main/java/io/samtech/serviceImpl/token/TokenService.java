package io.samtech.serviceImpl.token;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.models.User;
import io.samtech.entity.rdb.Token;
import io.samtech.exception.TokenBusinessException;
import io.samtech.repository.rdb.TokenRepository;
import io.samtech.security.currentSecurity.JwtService;
import io.samtech.serviceApi.token.ITokenService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

import static io.samtech.constants.CommonConstants.CommonMessages.*;

@Service
@AllArgsConstructor
public class TokenService implements ITokenService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public String generateAndSaveToken(User user) {
        Optional<Token> existingToken = tokenRepository.findTokenByUser(user);
        existingToken.ifPresent(tokenRepository::delete);
        String generateToken = jwtService.generateAccessToken(user);

        var token = Token.builder()
                .user(entityManager.merge(user))
                .token(generateToken)
                .tokenType(CommonConstants.TokenType.BEARER)
                .build();
        tokenRepository.save(token);
        return generateToken;

    }

    @Override
    public String validateReceivedToken(String token) {
       final Token receivedToken = tokenRepository.findByToken(token)
               .orElseThrow();
       if (receivedToken == null){
           return TOKEN_INVALID;
       }


       if (receivedToken.getExpiryTime().isBefore(LocalDateTime.now())){
           tokenRepository.delete(receivedToken);
           return TOKEN_EXPIRED;
       }
       return TOKEN_VALID;
    }

    @Override
    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }
}
