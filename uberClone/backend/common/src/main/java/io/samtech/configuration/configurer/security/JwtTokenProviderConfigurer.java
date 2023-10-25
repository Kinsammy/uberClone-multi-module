//package io.samtech.configuration.configurer.security;
//
//import io.samtech.repository.mongodb.MongoAccessTokenRepository;
//import io.samtech.repository.mongodb.MongoRefreshTokenRepository;
//import io.samtech.security.jwt.JwtTokenStore;
//import io.samtech.security.service.mongo.MongoJwtTokenStore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JwtTokenProviderConfigurer {
//    @Bean
//    @ConditionalOnMissingBean
//    JwtTokenStore jwtTokenStore(MongoAccessTokenRepository accessTokenRepo, MongoRefreshTokenRepository refreshTokenRepo) {
//        return new MongoJwtTokenStore(accessTokenRepo, refreshTokenRepo);
//    }
//}
