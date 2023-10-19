package io.samtech.serviceApi.token;

import io.samtech.entity.models.User;
import io.samtech.entity.rdb.Token;

import java.util.Optional;

public interface ITokenService {
    String generateAndSaveToken(User user);
    Optional<Token> validateReceivedToken(User user, String token);
    void deleteToken(Token token);
}
