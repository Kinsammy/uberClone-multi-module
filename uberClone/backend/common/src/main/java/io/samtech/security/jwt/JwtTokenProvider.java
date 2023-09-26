package io.samtech.security.jwt;

public interface JwtTokenProvider {
    JwtAccessToken generateToken(final String username, final String password, final boolean rememberMe);
    JwtAccessToken renewToken(final String jwtToken);
    void authorizeToken(final String jwtToken);
    boolean isSelfIssuer(final String jwtToken);
    void revokeToken(final String token);
}
