package io.samtech.entity.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sametech.library_management_system.data.models.users.Permission.*;


@RequiredArgsConstructor
public enum Role {
    LIBRARY_USER(
            Set.of(
                    LIBRARY_USER_REGISTER,
                    LIBRARY_USER_VERIFY,
                    LIBRARY_USER_LOGIN,
                    LIBRARY_USER_RESET_PASSWORD,
                    LIBRARY_USER_RESET_PASSWORD_MAIL,
                    LIBRARY_USER_REFRESH_TOKEN,
                    LIBRARY_USER_VIEW_ALL_BOOKS,
                    LIBRARY_USER_VIEW_A_BOOK

            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    LIBRARIAN_READ,
                    LIBRARIAN_CREATE,
                    LIBRARIAN_UPDATE,
                    LIBRARIAN_DELETE,
                    LIBRARY_USER_REGISTER,
                    LIBRARY_USER_VERIFY,
                    LIBRARY_USER_LOGIN,
                    LIBRARY_USER_RESET_PASSWORD,
                    LIBRARY_USER_RESET_PASSWORD_MAIL,
                    LIBRARY_USER_REFRESH_TOKEN,
                    LIBRARY_USER_VIEW_ALL_BOOKS,
                    LIBRARY_USER_VIEW_A_BOOK
            )
    ),
    LIBRARIAN(
            Set.of(
                    LIBRARIAN_READ,
                    LIBRARIAN_CREATE,
                    LIBRARIAN_UPDATE,
                    LIBRARIAN_DELETE,
                    LIBRARY_USER_LOGIN
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
