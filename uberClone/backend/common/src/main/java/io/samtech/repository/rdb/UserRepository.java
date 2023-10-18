//package io.samtech.repository.rdb;
//
//import io.samtech.entity.rdb.User;
//import org.springframework.data.repository.CrudRepository;
//
//import java.util.Optional;
//
//public interface UserRepository extends CrudRepository<User, Long> {
//    Optional<User> findUserByEmail(final String email);
//
//    Optional<User> findUserByPhoneNumber(final String phoneNumber);
//    Optional<User> findUserByUsername(final String username);
//    Optional<User> findUserById(final Long id);
//    Optional<User> findUserByPreferredUsername(final String preferredUsername);
//
//    boolean existsByEmail(final String email);
//
//    boolean existsByUsername(final String username);
//    boolean existsByPhoneNumber(final String phoneNumber);
//}
