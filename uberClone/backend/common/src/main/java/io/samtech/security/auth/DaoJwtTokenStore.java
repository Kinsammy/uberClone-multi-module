package io.samtech.security.auth;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.rdb.AccessToken;
import io.samtech.entity.rdb.RefreshToken;
import io.samtech.exception.RevokedJwtTokenException;
import io.samtech.repository.rdb.AccessTokenRepository;
import io.samtech.repository.rdb.RefreshTokenRepository;
import io.samtech.security.jwt.JwtTokenStore;
import io.samtech.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
@Slf4j
public class DaoJwtTokenStore implements JwtTokenStore {

    private final AccessTokenRepository accessTokenRepo;

    private final RefreshTokenRepository refreshTokenRepo;

    public DaoJwtTokenStore(AccessTokenRepository accessTokenRepo, RefreshTokenRepository refreshTokenRepo) {
        this.accessTokenRepo = accessTokenRepo;
        this.refreshTokenRepo = refreshTokenRepo;
    }


    @Override
    public void saveAccessToken(String id, Long userId, Date expiration) {
        AccessToken accessToken = AccessToken.builder()
                .newEntity()
                .id(id)
                .userId(userId)
                .expiredAt(DateUtils.dateToLocalDateTime(expiration))
                .status(CommonConstants.EntityStatus.ACTIVE)
                .build();
        accessTokenRepo.save(accessToken);
    }

    @Override
    public void saveRefreshToken(String id, String accessTokenId, Long userId, Date expiration) {
        RefreshToken refreshToken = RefreshToken.builder()
                .newEntity()
                .id(id)
                .accessTokenId(accessTokenId)
                .userId(userId)
                .expiredAt(DateUtils.dateToLocalDateTime(expiration))
                .status(CommonConstants.EntityStatus.ACTIVE)
                .build();
        refreshTokenRepo.save(refreshToken);
    }

    @Override
    public Long getUserIdByRefreshTokenId(String refreshTokenId) {
        return refreshTokenRepo.findActiveRefreshTokenBy(refreshTokenId)
                .map(RefreshToken::getUserId)
                .orElseThrow(RevokedJwtTokenException::new);
    }

    @Override
    public void inactiveAccessTokenById(String id) {
        accessTokenRepo.deactivateAccessTokenById(id);
    }

    @Override
    public void inactiveRefreshTokenById(String id) {
        refreshTokenRepo.deactivateRefreshTokenById(id);
    }

    @Override
    public void removeTokensByAccessTokenId(String accessTokenId) {
        refreshTokenRepo.deleteRefreshTokenByAccessTokenId(accessTokenId);
        accessTokenRepo.deleteById(accessTokenId);
    }

    @Override
    public boolean isAccessTokenExisted(String accessTokenId) {
        return accessTokenRepo.existsActiveAccessTokenById(accessTokenId);
    }

}
