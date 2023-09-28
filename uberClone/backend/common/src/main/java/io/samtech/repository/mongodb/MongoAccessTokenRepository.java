package io.samtech.repository.mongodb;

import io.samtech.entity.mongodb.MongoAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface MongoAccessTokenRepository extends CrudRepository<MongoAccessToken, String> {

}
