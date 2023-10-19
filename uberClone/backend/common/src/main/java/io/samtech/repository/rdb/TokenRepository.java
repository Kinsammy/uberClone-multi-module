package io.samtech.repository.rdb;

import io.samtech.entity.models.User;
import io.samtech.entity.rdb.Token;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
    
            SELECT t.*
           FROM token t
           INNER JOIN user u ON t.user_id = u.id
           WHERE u.id = :userId AND (t.expired = false OR t.revoked = false);
    """)
    Set<Token> findAllValidTokensByUserId(Long user_id);

    Optional<Token> findByToken(String token);

    Optional<Token> findTokenByUserAndToken(User appUser, String token);

    Optional<Token> findTokenByUser(User appUser);

}
