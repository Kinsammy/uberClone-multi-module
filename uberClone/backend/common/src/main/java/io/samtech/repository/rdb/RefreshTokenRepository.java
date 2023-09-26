package io.samtech.repository.rdb;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.rdb.RefreshToken;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    void deleteRefreshTokenByAccessTokenId(final String accessTokenId);

    Optional<RefreshToken> findRefreshTokenByIdAndStatus(final String id, final Integer status);

    default Optional<RefreshToken> findActiveRefreshTokenBy(final String id) {
        return findRefreshTokenByIdAndStatus(id, CommonConstants.EntityStatus.ACTIVE);
    }

    @Modifying
    @Query("update refresh_tokens rt set rt.status = 0 where rt.id = :id")
    void deactivateRefreshTokenById(@Param("id") String id);
}
