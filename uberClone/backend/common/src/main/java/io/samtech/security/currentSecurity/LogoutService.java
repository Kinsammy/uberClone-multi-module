package io.samtech.security.currentSecurity;

import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    private String jwtSecretKey;
    private Long jwtExpiration;

}
