package io.samtech.serviceImpl.user;

import io.samtech.application.event.publisher.UserEventPublisher;
import io.samtech.constants.CommonConstants;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RegisterUserRequest;
import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.dto.response.ResetPasswordResponse;
import io.samtech.entity.models.Role;
import io.samtech.entity.models.User;
import io.samtech.exception.UserAlreadyExistByEmailException;
import io.samtech.exception.UserAlreadyExitByPhoneNumberException;
import io.samtech.exception.UserVerifyCodeException;
import io.samtech.repository.model.UserRepository;
import io.samtech.serviceApi.user.UserService;
import io.samtech.utils.DataProcessor;
import io.samtech.utils.DefaultInstance;
import io.samtech.utils.PasswordGeneratorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import static io.samtech.configuration.message.Translator.eval;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisher userEventPublisher;
    @Value(value = "${spring.application.secret-key}")
    private String appKey;


    @Override
    public void createProfileTypeUser(User user) {
        User createUser = createInternalUser(user);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyAccountWithToken(String token) {
        final Long userId = getUserIdFromVerifyToken(token);
        userRepository.findUserById(userId).ifPresent(user -> {
            if (Objects.equals(user.getEmailVerified(), CommonConstants.EntityStatus.UNVERIFIED)){
                user.setEmailVerified(CommonConstants.EntityStatus.VERIFIED);
                userRepository.save(user);
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
                .role(user.getRole())
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
    public void sendResetPasswordToEmail(String email) {

    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        return null;
    }
}
