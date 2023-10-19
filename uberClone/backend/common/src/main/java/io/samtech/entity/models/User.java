package io.samtech.entity.models;


import io.samtech.entity.rdb.Token;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Token> tokens;
//    @OneToMany
//    private Set<Profile> profile;

//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return role.getAuthorities();
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
////    @Override
////    public boolean isEnabled() {
////        return enabled;
////    }
//
//
}
