package io.samtech.entity.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static io.samtech.entity.models.Permission.*;


@RequiredArgsConstructor
public enum Role {

    BASIC(Set.of(
            BASIC_READ
    )),

    PASSENGER(
            Set.of(
                    PASSENGER_REGISTER,
                    PASSENGER_VERIFY,
                    PASSENGER_LOGIN,
                    PASSENGER_RESET_PASSWORD,
                    PASSENGER_RESET_PASSWORD_MAIL,
                    PASSENGER_REFRESH_TOKEN,
                    PASSENGER_VIEW_ALL_BOOKS,
                    PASSENGER_VIEW_A_BOOK

            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    DRIVER_REGISTER,
                    DRIVER_VERIFY,
                    DRIVER_LOGIN,
                    DRIVER_RESET_PASSWORD,
                    DRIVER_RESET_PASSWORD_MAIL,
                    DRIVER_REFRESH_TOKEN,
                    DRIVER_VIEW_ALL_BOOKS,
                    DRIVER_VIEW_A_BOOK,
                    PASSENGER_REGISTER,
                    PASSENGER_VERIFY,
                    PASSENGER_LOGIN,
                    PASSENGER_RESET_PASSWORD,
                    PASSENGER_RESET_PASSWORD_MAIL,
                    PASSENGER_REFRESH_TOKEN,
                    PASSENGER_VIEW_ALL_BOOKS,
                    PASSENGER_VIEW_A_BOOK
            )
    ),


    DRIVER(
            Set.of(
                    DRIVER_REGISTER,
                    DRIVER_VERIFY,
                    DRIVER_LOGIN,
                    DRIVER_RESET_PASSWORD,
                    DRIVER_RESET_PASSWORD_MAIL,
                    DRIVER_REFRESH_TOKEN,
                    DRIVER_VIEW_ALL_BOOKS,
                    DRIVER_VIEW_A_BOOK
            )
    )
    ;

    @Getter
    private final Set<Permission> permissions;


    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities  = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
