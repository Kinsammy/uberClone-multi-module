package io.samtech.entity.rdb;

import io.samtech.constants.CommonConstants;
import io.samtech.security.SecuredUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static io.samtech.constants.CommonConstants.EntityName.USER;

@Getter
@Setter
@Table(value = USER)
public class User extends AbstractJdbcEntity<Long> implements SecuredUser {

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

    private Integer enabled;

    private Integer locked;

    @MappedCollection(idColumn = "user_id")
    private Set<Profile> profile;

    @Transient
    private Set<Role> roles = new HashSet<>();
    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPreferredUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getFamilyName() {
        return null;
    }

    @Override
    public String getMiddleName() {
        return null;
    }

    @Override
    public String getGivenName() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public Integer getGender() {
        return null;
    }

    @Override
    public LocalDate getBirthdate() {
        return null;
    }

    @Override
    public Integer getEnabled() {
        return null;
    }

    @Override
    public Integer getLocked() {
        return null;
    }

    @Override
    public Set<String> getAuthorityNames() {
        return null;
    }
}
