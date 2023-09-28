package io.samtech.security.service.mongo;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.mongodb.MongoAccessToken;
import io.samtech.entity.mongodb.MongoRefreshToken;
import io.samtech.repository.mongodb.MongoAccessTokenRepository;
import io.samtech.repository.mongodb.MongoRefreshTokenRepository;
import io.samtech.security.jwt.JwtTokenStore;
import io.samtech.utils.DateUtils;

import java.util.Date;

public class MongoJwtTokenStore implements JwtTokenStore {
    private final MongoAccessTokenRepository accessTokenRepo;

    private final MongoRefreshTokenRepository refreshTokenRepo;

    public MongoJwtTokenStore(MongoAccessTokenRepository accessTokenRepo, MongoRefreshTokenRepository refreshTokenRepo) {
        this.accessTokenRepo = accessTokenRepo;
        this.refreshTokenRepo = refreshTokenRepo;
    }
    @Override
    public void saveAccessToken(String id, Long userId, Date expiration) {
        MongoAccessToken accessToken = MongoAccessToken.builder()
                .id(id)
                .userId(userId)
                .status(CommonConstants.EntityStatus.ACTIVE)
                .expiredAt(DateUtils.dateToLocalDateTime(expiration))
                .build();
        accessTokenRepo.save(accessToken);

    }

    @Override
    public void saveRefreshToken(String id, String accessTokenId, Long userId, Date expiration) {
        MongoRefreshToken refreshToken = MongoRefreshToken.builder()
                .id(id)
                .accessTokenId(accessTokenId)
                .userId(userId)
                .expiredAt(DateUtils.dateToLocalDateTime(expiration))
                .build();
        refreshTokenRepo.save(refreshToken);
    }

    @Override
    public Long getUserIdByRefreshTokenId(String refreshTokenId) {
        return refreshTokenRepo.findActiveMongoRefreshTokenId(refreshTokenId)
    }

    @Override
    public void inactiveAccessTokenById(String id) {

    }

    @Override
    public void inactiveRefreshTokenById(String id) {

    }

    @Override
    public boolean isAccessTokenExisted(String accessTokenId) {
        return false;
    }

    @Override
    public void removeTokensByAccessTokenId(String accessTokenId) {

    }
}
