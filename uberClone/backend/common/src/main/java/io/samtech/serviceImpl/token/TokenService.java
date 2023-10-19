package io.samtech.serviceImpl.token;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.models.User;
import io.samtech.entity.rdb.Token;
import io.samtech.repository.rdb.TokenRepository;
import io.samtech.security.currentSecurity.JwtService;
import io.samtech.serviceApi.token.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<Token> validateReceivedToken(User appUser, String token) {
        return Optional.empty();
    }

    @Override
    public void deleteToken(Token token) {

    }
}
