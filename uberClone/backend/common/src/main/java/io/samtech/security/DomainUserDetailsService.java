//package io.samtech.security;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public interface DomainUserDetailsService extends UserDetailsService {
//
//    SecuredUserDetails loadUserById(final Long userId);
//
//    @Override
//    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
//
//    SecuredUserDetails loadUserByPreferredUsername(final String preferredUserName);
//}
