package io.samtech.entity.models;

import io.samtech.entity.rdb.AbstractJdbcEntity;
import io.samtech.entity.rdb.Token;
import io.samtech.security.SecuredUser;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static io.samtech.constants.CommonConstants.EntityName.USER;

@Getter
@Setter
@Table(value = USER)
public class User extends AbstractJdbcEntity<Long> implements UserDetails {

    private String username;

    private String preferredUsername;

    private String password;

    private String rawPassword;

    private String email;

    private Integer emailVerified;

    private String familyName;

    private String middleName;

    private String givenName;

    private String name;

    private String unsigned_name;

    private String phoneNumber;

    private Integer phoneNumberVerified;

    private Integer gender;

    private LocalDate birthdate;

    private boolean enabled;

    private Integer locked;
    @Enumerated(EnumType.STRING)
    private Role role;
    @MappedCollection(idColumn = "user_id")
    private List<Token> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
