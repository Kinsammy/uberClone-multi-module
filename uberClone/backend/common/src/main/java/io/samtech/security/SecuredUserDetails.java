package io.samtech.security;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.models.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class SecuredUserDetails implements UserDetails {
    @NonNull
    private final User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return StreamEx.of(user.getRole().getAuthorities())
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Objects.equals(this.user.getLocked(), CommonConstants.EntityStatus.UNLOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(user.getEnabled(), CommonConstants.EntityStatus.ENABLED);
    }

}
