package io.samtech.repository.rdb;

import io.samtech.entity.rdb.Token;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Set;

public interface TokenRepository extends CrudRepository<Token, Long> {
    @Query("""
    select t from Token

    """)
    Set<Token> findAllValidTokensByUserId(Event.ID user_id);
}
