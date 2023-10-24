package io.samtech.serviceImpl.token;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.models.User;
import io.samtech.entity.rdb.Token;
import io.samtech.exception.TokenBusinessException;
import io.samtech.repository.rdb.TokenRepository;
import io.samtech.security.currentSecurity.JwtService;
import io.samtech.serviceApi.token.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

import static io.samtech.constants.CommonConstants.CommonMessages.*;

@Service
@AllArgsConstructor
public class TokenService implements ITokenService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public String generateAndSaveToken(User user) {
        Optional<Token> existingToken = tokenRepository.findTokenByUser(user);
        existingToken.ifPresent(tokenRepository::delete);
        String generateToken = jwtService.generateAccessToken(user);

        var token = Token.builder()
                .token(generateToken)
                .tokenType(CommonConstants.TokenType.BEARER)
                .build();
        tokenRepository.save(token);
        return generateToken;

    }

    @Override
    public String validateReceivedToken(String token) {
       final Token receivedToken = tokenRepository.findByToken(token)
               .orElseThrow(TokenBusinessException::new);
       if (receivedToken == null){
           return TOKEN_INVALID;
       }

       final Calendar calendar = Calendar.getInstance();
       if (receivedToken.getExpiryTime().isAfter(LocalDateTime.now())){
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
