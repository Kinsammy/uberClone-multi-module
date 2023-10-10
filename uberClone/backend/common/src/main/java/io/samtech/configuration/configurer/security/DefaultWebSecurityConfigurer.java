package io.samtech.configuration.configurer.security;

import io.samtech.constants.CommonConstants;
import io.samtech.security.jwt.JwtSecurityAdapter;
import io.samtech.security.jwt.JwtTokenProvider;
import io.samtech.security.jwt.RestAuthenticationEntryPoint;
import io.samtech.security.jwt.SimpleJwtAuthenticationFilter;
import io.samtech.security.oauth2.Oauth2JwtAuthenticationConverter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class DefaultWebSecurityConfigurer {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationProvider authenticationProvider;
    private final Oauth2JwtAuthenticationConverter oauth2JwtAuthenticationConverter;
    private final HandlerExceptionResolver resolver;
    private final SimpleJwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] AUTHENTICATION_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "webjars/**",
            "/swagger-ui.html"
    };


//    public DefaultWebSecurityConfigurer(JwtTokenProvider jwtTokenProvider,
//                                        Oauth2JwtAuthenticationConverter oauth2JwtAuthenticationConverter,
//                                        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
//                                        AuthenticationProvider authenticationProvider, SimpleJwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.oauth2JwtAuthenticationConverter = oauth2JwtAuthenticationConverter;
//        this.resolver = resolver;
//        this.authenticationProvider = authenticationProvider;
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> {
                    headers
                            .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                            .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                            .permissionsPolicy(permissions -> permissions.policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"));
                })


                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                                .requestMatchers("/webjars/**", "/error/**").permitAll()
                                .requestMatchers("/address/**").permitAll()
                                .requestMatchers(AUTHENTICATION_WHITELIST).permitAll()
                                .anyRequest()
                                .authenticated()
                                .requestMatchers("/auth/token", "/auth/renew-token", "/auth/forgot-password", "/auth/forgot-password-complete","/auth/otp/verify","/driver/**","/admin/**").permitAll()
                                .requestMatchers("/actuator/**").hasAuthority(CommonConstants.Privilege.READ_PRIVILEGE)
                                .anyRequest()
                                .authenticated());

//        http
//                .formLogin(form -> {
//                    try {
//                        form.disable().logout(AbstractHttpConfigurer::disable)
//                                .apply(securityConfigurerAdapter());
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                });

        http
                .oauth2ResourceServer(oauth2 ->
                {
                    oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(oauth2JwtAuthenticationConverter));
                    oauth2.authenticationEntryPoint(new RestAuthenticationEntryPoint(resolver));
                });

        http.exceptionHandling(
                exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(new RestAuthenticationEntryPoint(resolver))
        );
        return http.build();






    }

    private JwtSecurityAdapter securityConfigurerAdapter() {
        return new JwtSecurityAdapter(jwtTokenProvider, new RestAuthenticationEntryPoint(resolver));
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


