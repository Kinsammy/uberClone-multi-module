package io.samtech.security.currentSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.samtech.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtService {

    @Value("${app.common.jwt.issuer}")
    private String issuer;

    @Value("${app.common.jwt.access-token-expiration-in-minutes}")
    private long accessTokenExpirationInMinutes;

    @Value("${app.common.jwt.refresh-token-expiration-in-minutes}")
    private long refreshTokenExpirationInMinutes;

    @Value("${app.common.jwt.remember-me-expiration-in-minutes}")
    private long rememberMeExpirationInMinutes;

    @Value("${app.common.jwt.secret-key}")
    private String jwtSecretKey;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolve) {
        final Claims claims = extractAllClaims(token);
        return claimsResolve.apply(claims);
    }

    public String generateAccessToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails){
        return buildToken(extractClaims, userDetails, accessTokenExpirationInMinutes);
    }

    public String generateAccessToken(UserDetails userDetails){
        return generateAccessToken(new HashMap<>(), userDetails);
    }


    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshTokenExpirationInMinutes);
    }



    private String buildToken(Map<String, Object> extractClaims,
                              UserDetails userDetails,
                              long accessTokenExpirationInMinutes) {
        return Jwts.builder()
                .setIssuer(issuer)
                .setClaims(extractClaims)
                .setAudience(CommonConstants.TokenAudience.ACCESS_TOKEN)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationInMinutes))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//        return (Key) (this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build());
//    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
