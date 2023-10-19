package io.samtech.serviceImpl.profile;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.models.Role;
import io.samtech.entity.models.User;
import io.samtech.exception.UserAlreadyExitByPhoneNumberException;
import io.samtech.repository.model.UserRepository;
import io.samtech.serviceApi.profille.UserHandlerServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PassengerUserHandlerServiceImpl implements UserHandlerServiceApi {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User createBasicUser(User user) {
       log.info("calling customer user creation");
       if (userRepository.existsByEmail(user.getEmail())){
           throw new UserAlreadyExitByPhoneNumberException();
       }

       if (userRepository.existsByPhoneNumber(user.getPhoneNumber())){
           throw new UserAlreadyExitByPhoneNumberException();
       }

       Role passengerRole = Role.PASSENGER;
       User userToSave = User.builder()
               .email(user.getEmail())
               .emailVerified(CommonConstants.EntityStatus.VERIFIED)
               .phoneNumber(user.getPhoneNumber())
               .rawPassword(user.getRawPassword())
               .preferredUsername(UUID.randomUUID().toString())
               .password(passwordEncoder.encode(user.getPassword()))
//               .profile(user.getProfile())
               .role(passengerRole)
               .enabled(CommonConstants.EntityStatus.ENABLED)
               .locked(CommonConstants.EntityStatus.UNLOCKED)
               .build();
       return userRepository.save(userToSave);
    }
}
