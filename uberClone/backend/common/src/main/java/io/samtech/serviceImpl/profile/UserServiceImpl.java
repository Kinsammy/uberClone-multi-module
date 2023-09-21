package io.samtech.serviceImpl.profile;

import com.naharoo.commons.mapstruct.MappingFacade;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RegisterUserRequest;
import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.dto.response.ResetPasswordResponse;
import io.samtech.entity.rdb.User;
import io.samtech.exception.UserAlreadyExistByEmailException;
import io.samtech.exception.UserAlreadyExitByPhoneNumberException;
import io.samtech.repository.rdb.UserRepository;
import io.samtech.serviceApi.profille.UserHandlerServiceApi;
import io.samtech.serviceApi.role.RoleService;
import io.samtech.serviceApi.user.UserService;
import io.samtech.utils.DataProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    private UserHandlerServiceApi userHandlerServiceApi;
    private final MappingFacade mapper;
    private final PasswordEncoder passwordEncoder;
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
        final String name = DataProcessor.getInstance().get
    }

    @Override
    public User findActiveUserById(Long id) {
        return null;
    }

    @Override
    public User findActiveUserByPreferredUsername(String preferredUsername) {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return false;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public void creatUser(CreateUserRequest request) {

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
