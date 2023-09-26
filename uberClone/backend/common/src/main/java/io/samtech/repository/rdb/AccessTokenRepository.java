package io.samtech.repository.rdb;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.rdb.AccessToken;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    boolean existsAccessTokenByIdAndStatus(String id, Integer status);

    default boolean existsActiveAccessTokenById(String id) {
        return existsAccessTokenByIdAndStatus(id, CommonConstants.EntityStatus.ACTIVE);
    }

    @Modifying
    @Query("update access_tokens ac set ac.status = 0 where ac.id = :id")
    void deactivateAccessTokenById(@Param("id") String id);
}
