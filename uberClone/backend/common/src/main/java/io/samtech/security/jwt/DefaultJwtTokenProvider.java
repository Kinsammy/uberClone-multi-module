package io.samtech.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.samtech.exception.InvalidJwtTokenException;
import io.samtech.exception.RequestedEntityNotFoundException;
import io.samtech.security.DomainUserDetailsService;
import io.samtech.security.SecuredUser;
import io.samtech.utils.AppContextUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j

public class DefaultJwtTokenProvider implements JwtTokenProvider {

    private static final ObjectMapper JACKSON_MAPPER;
    protected final AuthenticationManagerBuilder authenticationManagerBuilder;
    protected final DomainUserDetailsService userDetailsService;
    protected final JwtTokenStore tokenStore;
    private final UserDetailsChecker postCheckUserStatus = new AccountStatusUserDetailsChecker();

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

    private JwtParser jwtParser;
    private Key secretKey;


    public DefaultJwtTokenProvider(AuthenticationManagerBuilder authenticationManagerBuilder, DomainUserDetailsService userDetailsService, JwtTokenStore tokenStore) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
    }

    private static Claims getClaims(final JwtParser jwtParser, final String jwt){
        try {
            return jwtParser.parseClaimsJwt(jwt).getBody();
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException |
                 PrematureJwtException | IllegalArgumentException e) {
            log.trace("Invalid JWT jwt", e);
            throw new InvalidJwtTokenException();
        }
    }

    @PostConstruct
    void afterPropertiesSet(){
        byte[] keybytes = Decoders.BASE64.decode(jwtSecretKey);
        this.secretKey = Keys.hmacShaKeyFor(keybytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public JwtAccessToken generateToken(String username, String password, boolean rememberMe) {
        Authentication authenticationToken= UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return createToken(rememberMe);
    }

    private JwtAccessToken createToken(final boolean rememberMe) {
        SecuredUser currentUser = AppContextUtils.getCurrentLoginUser()
                .orElseThrow(RequestedEntityNotFoundException::new);

        final Date issuedAt = new Date();
        final String accessTokenId = UUID.randomUUID().toString();
        final Date expirationAccessToken = getExpirationDate(issuedAt, accessTokenExpirationInMinutes, rememberMe);
        final String accessToken = createAccessToken(accessTokenId, currentUser.getId(), expirationAccessToken);
    }

    private Date getExpirationDate(final Date issuedAt, final long defaultExpiration, final boolean rememberMe) {
        return new Date(issuedAt.getTime() + (rememberMe ? (defaultExpiration + rememberMeExpirationInMinutes) : defaultExpiration) * 1000 * 60);
    }

    @Override
    public JwtAccessToken renewToken(String jwtToken) {
        return null;
    }

    @Override
    public void authorizeToken(String jwtToken) {

    }

    @Override
    public boolean isSelfIssuer(String jwtToken) {
        return false;
    }

    @Override
    public void revokeToken(String token) {

    }
}
