package io.samtech.serviceImpl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.samtech.application.event.publisher.UserEventPublisher;
import io.samtech.constants.CommonConstants;
import io.samtech.dto.request.AuthenticationRequest;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RegisterUserRequest;
import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.dto.response.AuthenticationResponse;
import io.samtech.entity.models.Role;
import io.samtech.entity.models.User;
import io.samtech.entity.rdb.Token;
import io.samtech.exception.*;
import io.samtech.repository.model.UserRepository;
import io.samtech.repository.rdb.TokenRepository;
import io.samtech.security.currentSecurity.JwtService;
import io.samtech.serviceApi.token.ITokenService;
import io.samtech.serviceApi.user.UserService;
import io.samtech.utils.DataProcessor;
import io.samtech.utils.DefaultInstance;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static io.samtech.configuration.message.Translator.eval;
import static io.samtech.constants.CommonConstants.CommonMessages.TOKEN_VALID;
import static io.samtech.constants.CommonConstants.SecurityString.AUTHORIZATION_HEADER;
import static io.samtech.constants.CommonConstants.SecurityString.BEARER_TOKEN_PREFIX;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisher userEventPublisher;
    private final ITokenService tokenService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value(value = "${spring.application.secret-key}")
    private String appKey;


    @Override
    public void createProfileTypeUser(User user) {
        User createUser = createInternalUser(user);
    }

//todo -> this method is not yet complete because after verification the token suppose to be deleted
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyAccountWithToken(String token) {
        final Long userId = getUserIdFromVerifyToken(token);
//        String receivedToken = tokenService.validateReceivedToken(token);
                userRepository.findUserById(userId).ifPresent(user -> {
            if (Objects.equals(user.getEmailVerified(), CommonConstants.EntityStatus.UNVERIFIED)){
                user.setEmailVerified(CommonConstants.EntityStatus.VERIFIED);
                user.setLocked(CommonConstants.EntityStatus.LOCKED);
                userRepository.save(user);
//                tokenRepository.deleteByToken(receivedToken);
            }
            else throw new UserVerifyCodeException.Verified();
        });
    }



    private Long getUserIdFromVerifyToken(String token) {
        try{
            byte[] rawData = DataProcessor.decrypt(Base64.getDecoder().decode(token), appKey);
            return DefaultInstance.OBJECT_MAPPER.readValue(rawData, Long.class);
        }
        catch(Exception e){
            log.error("Cannot decode info from verify code", e);
            throw new UserVerifyCodeException.Invalid();
        }
    }

    private User createInternalUser(User user) {
        log.info("calling user creation");
        if (existsByEmail(user.getEmail())){
            throw new UserAlreadyExistByEmailException();
        }

        if (existsByPhoneNumber(user.getPhoneNumber())){
            throw new UserAlreadyExitByPhoneNumberException();
        }

        final String fullName = DataProcessor.joinWihSpaceDelimiter(user.getGivenName(), user.getMiddleName(), user.getFamilyName());

        User userDetails = User.builder()
                .name(fullName)
                .locked(user.getLocked())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())

                .familyName(user.getFamilyName())
                .givenName(user.getGivenName())
                .username(UUID.randomUUID().toString())
                .unsigned_name(StringUtils.stripAccents(fullName))
                .role(Role.BASIC)
                .rawPassword(user.getRawPassword())
                .password(passwordEncoder.encode(user.getRawPassword()))
                .enabled(CommonConstants.EntityStatus.ENABLED)
                .build();
        return userRepository.save(userDetails);
    }

    @Override
    public User findActiveUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException(eval("app.user.exception.not-found")));
    }



    @Override
    public User findActiveUserByPreferredUsername(String preferredUsername) {
        return userRepository.findUserByPreferredUsername(preferredUsername)
                .orElseThrow(()-> new UsernameNotFoundException(eval("app.user.exception.not-found")));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(eval("app.user.exception.not-found")));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(eval("app.user.exception.not-found")));
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException(eval("app.user.exception.not-found")));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void creatUser(CreateUserRequest request) {
        User user = CreateUserDetails(request);
        userEventPublisher.publishVerificationEvent(user);
    }

    @NotNull
    public User CreateUserDetails(CreateUserRequest request) {
        if (existsByEmail(request.getEmail())){
            throw new UserAlreadyExistByEmailException();
        }

        if (existsByPhoneNumber(request.getPhoneNumber())){
            throw new UserAlreadyExitByPhoneNumberException();
        }

        final String fullName = DataProcessor.joinWihSpaceDelimiter(request.getGivenName(), request.getMiddleName(), request.getFamilyName());

        User user = User.builder()
                .name(fullName)
                .locked(CommonConstants.EntityStatus.UNLOCKED)
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())

                .familyName(request.getFamilyName())
                .givenName(request.getGivenName())
                .username(UUID.randomUUID().toString())
                .unsigned_name(StringUtils.stripAccents(fullName))
                .role(Role.BASIC)
                .rawPassword(request.getRawPassword())
                .password(passwordEncoder.encode(request.getRawPassword()))
                .enabled(CommonConstants.EntityStatus.ENABLED)
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public void registerUser(RegisterUserRequest request) {

    }

    @Override
    public void verifyCreatedUserEmail(String code) {

    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(UserNotExistedException::new);

        var jwtToken = jwtService.generateAccessToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
        return getAuthenticationResponse(jwtToken, refreshToken);
    }

    @Override
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findUserByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateAccessToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = getAuthenticationResponse(accessToken, refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private AuthenticationResponse getAuthenticationResponse(String jwtToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(CommonConstants.TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void requestResetPassword(String email) {
        var foundUser = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotExistedException::new);

            userEventPublisher.publishResetPassword(foundUser);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        String receivedToken = tokenService.validateReceivedToken(request.getToken());

        if (!Objects.equals(receivedToken, TOKEN_VALID)) {
            throw new InvalidTokenException(receivedToken);
        } else {
            Token token = tokenRepository.findByToken(request.getToken())
                    .orElseThrow(
                            () -> new TokenBusinessException("Token not found"));


            if (token.getUser() == null) {
                throw new TokenBusinessException("Token is not associated with a valid user");
            }

            User user = findActiveUserById(token.getUser().getId());

            if (user == null) {
                throw new TokenBusinessException("User associated with the token not found");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            saveUser(user);
            tokenRepository.deleteByToken(request.getToken());
        }
    }

    @Override
    public void uploadProfileImage(MultipartFile profileImage, Long userId) {

    }

}
