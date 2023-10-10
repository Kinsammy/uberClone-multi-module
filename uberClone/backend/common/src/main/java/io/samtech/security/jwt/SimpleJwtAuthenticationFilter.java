package io.samtech.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

@Slf4j
@Component
public class SimpleJwtAuthenticationFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SimpleJwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        doFilterInternal(request, response, filterChain);
    }

    private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);
        if (StringUtils.hasText(jwt) && jwtTokenProvider.isSelfIssuer(jwt)) {
            try {
                this.jwtTokenProvider.authorizeToken(jwt);
                filterChain.doFilter(new  HiddenTokenRequestWrapper((HttpServletRequest) request),response);
            }catch (AuthenticationException exception){
                SecurityContextHolder.clearContext();
                this.logger.trace("Failed to process authentication request", exception);
                this.authenticationEntryPoint.commence((HttpServletRequest) request, (HttpServletResponse) response, exception);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    static class HiddenTokenRequestWrapper extends HttpServletRequestWrapper {
        /**
         * construct a wrapper for this request
         *
         * @param request
         */

        public HiddenTokenRequestWrapper(HttpServletRequest request){
            super(request);
        }
        @Override
        public String getHeader(String name) {
            return Objects.equals(name, AUTHORIZATION_HEADER) ? null : super.getHeader(name);
        }

        /**
         * get the Header names
         */
        @Override
        public Enumeration<String> getHeaderNames() {
            return super.getHeaderNames();
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            return Objects.equals(name, AUTHORIZATION_HEADER) ? null : super.getHeaders(name);
        }

    }

}
