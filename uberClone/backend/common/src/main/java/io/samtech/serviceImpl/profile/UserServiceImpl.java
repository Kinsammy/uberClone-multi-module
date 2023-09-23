package io.samtech.serviceImpl.profile;

import com.naharoo.commons.mapstruct.MappingFacade;
import io.samtech.application.event.publisher.UserEventPublisher;
import io.samtech.constants.CommonConstants;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RegisterUserRequest;
import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.dto.response.ResetPasswordResponse;
import io.samtech.entity.rdb.Role;
import io.samtech.entity.rdb.User;
import io.samtech.exception.UserAlreadyExistByEmailException;
import io.samtech.exception.UserAlreadyExitByPhoneNumberException;
import io.samtech.repository.rdb.UserRepository;
import io.samtech.serviceApi.profille.UserHandlerServiceApi;
import io.samtech.serviceApi.role.RoleService;
import io.samtech.serviceApi.user.UserService;
import io.samtech.utils.DataProcessor;
import io.samtech.utils.PasswordGeneratorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static io.samtech.configuration.message.Translator.eval;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    private UserHandlerServiceApi userHandlerServiceApi;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisher userEventPublisher;

    @Override
    public void createProfileTypeUser(User user) {
        User creatUser = createUserInternal(user);
    }

    private User createUserInternal(User user) {
        log.info("calling user creation");
        if (existsByEmail(user.getEmail())){
            throw new UserAlreadyExistByEmailException();
        }
        if (existsByPhoneNumber(user.getPhoneNumber())){
            throw new UserAlreadyExitByPhoneNumberException();
        }
        final String name = DataProcessor.joinWihSpaceDelimiter(user.getGivenName(), user.getMiddleName(), user.getFamilyName());

        User userBuild = User.builder()
                .name(name)
                .locked(user.getLocked())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .profile(user.getProfile())
                .familyName(user.getFamilyName())
                .givenName(user.getGivenName())
                .username(UUID.randomUUID().toString())
                .unsigned_name(StringUtils.stripAccents(name))
                .roles(user.getRoles())
                .rawPassword(user.getRawPassword())
                .password(passwordEncoder.encode(user.getRawPassword()))
                .enabled(CommonConstants.EntityStatus.ENABLED)
                .build();
        return userRepository.save(userBuild);
    }

    @Override
    public User findActiveUserById(final Long userId) {
        var user = userRepository.findUserById(userId)
                .orElseThrow(()-> new UsernameNotFoundException(eval("app.user.exception.not-found")));
        fulfillUserInfo(user);
        return user;
    }

    private void fulfillUserInfo(User user) {
        Set<Long> roleIds = user.roleIds();
        Set<Role> roles = roleService.findAllActiveByIds(roleIds);
        user.setRoles(roles);
    }

    @Override
    public User findActiveUserByPreferredUsername(String preferredUsername) {
        var user = userRepository.findUserByPreferredUsername(preferredUsername)
                .orElseThrow(()-> new UsernameNotFoundException(eval("app.user.exception.not-found")));
        fulfillUserInfo(user);
        return user;
    }

    @Override
    public User findUserByUsername(final String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(eval("app.user.exception.not-found")));
        fulfillUserInfo(user);
        return user;
    }

    @Override
    public User findUserByEmail(final String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(eval("app.user.exception.not-found")));
        fulfillUserInfo(user);
        return user;
    }

    @Override
    public User findUserByPhoneNumber(final String phoneNumber) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException(eval("app.user.exception.not-found")));
        fulfillUserInfo(user);
        return user;
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
        var user = mapper.map(request, User.class);

        Set<Long> filteredRoleIds = StreamEx.of(request.getRoleIds())
                .filter(roleId -> !Objects.equals(roleId, roleService.findActiveRoleById(1L).getId()))
                .toImmutableSet();

        Set<Role> roles = roleService.findAllActiveByIds(filteredRoleIds);
        user.setRoles(roles);
        final String password = PasswordGeneratorUtils.generateStrongPassword();
        user.setRawPassword(password);
        user.setLocked(CommonConstants.EntityStatus.UNLOCKED);
        createUserInternal(user);

        userEventPublisher.publishVerificationEvent(user);

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
