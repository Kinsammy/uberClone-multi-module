package io.samtech.repository.rdb;

import io.samtech.entity.rdb.Token;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TokenRepository extends CrudRepository<Token, Long> {
    @Query("""
    
            SELECT t.*
           FROM tokens t
           INNER JOIN users u ON t.user_id = u.id
           WHERE u.id = :userId AND (t.expired = false OR t.revoked = false);
    """)
    Set<Token> findAllValidTokensByUserId(Long user_id);

    Optional<Token> findByToken(String token);
}
