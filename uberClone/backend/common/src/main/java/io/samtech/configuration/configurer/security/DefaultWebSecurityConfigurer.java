package io.samtech.configuration.configurer.security;

import io.samtech.security.jwt.JwtTokenProvider;
import io.samtech.security.oauth2.Oauth2JwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
@Configuration
public class DefaultWebSecurityConfigurer {
    private final JwtTokenProvider jwtTokenProvider;
    private final Oauth2JwtAuthenticationConverter oauth2JwtAuthenticationConverter;
    private final HandlerExceptionResolver resolver;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.referrerPolicy(referrer-> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)))
                .headers(headers -> headers.permissionsPolicy(permissions-> permissions.policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                                .requestMatchers("/webjars/**", "/error/**").permitAll()
                                .requestMatchers("/address/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/user/**").permitAll()
                                .requestMatchers("/auth/token", "/auth/renew-token", "/auth/forgot-password", "/auth/forgot-password-complete","/auth/otp/verify","/customer/**","/admin/**").permitAll()
                                .antMatchers("/actuator/**").hasAuthority(CommonConstants.Privilege.READ_PRIVILEGE)




                )






    }

    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration= new CorsConfiguration();
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}

}
