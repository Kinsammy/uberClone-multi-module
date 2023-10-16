package io.samtech.entity.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    LIBRARY_USER_REGISTER("library_user:register"),
    LIBRARY_USER_VERIFY("library_user:verify"),
    LIBRARY_USER_LOGIN("library_user:login"),
    LIBRARY_USER_RESET_PASSWORD_MAIL("library_user:reset_password_mail"),
    LIBRARY_USER_RESET_PASSWORD("library_user:reset_password"),
    LIBRARY_USER_REFRESH_TOKEN("library_user:refresh_token"),
    LIBRARY_USER_VIEW_ALL_BOOKS("library_user:view_all_books"),
    LIBRARY_USER_VIEW_A_BOOK("library_user:view_a_book"),
    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    LIBRARIAN_READ("librarian:read"),
    LIBRARIAN_CREATE("librarian:create"),
    LIBRARIAN_UPDATE("librarian:update"),
    LIBRARIAN_DELETE("librarian:delete")
    ;

    @Getter
    private final String permission;
}
