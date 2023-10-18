package io.samtech.entity.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    BASIC_READ("basic:read"),
    PASSENGER_REGISTER("passenger:register"),
    PASSENGER_VERIFY("passenger:verify"),
    PASSENGER_LOGIN("passenger:login"),
    PASSENGER_RESET_PASSWORD_MAIL("passenger:reset_password_mail"),
    PASSENGER_RESET_PASSWORD("passenger:reset_password"),
    PASSENGER_REFRESH_TOKEN("passenger:refresh_token"),
    PASSENGER_VIEW_ALL_BOOKS("passenger:view_all_books"),
    PASSENGER_VIEW_A_BOOK("passenger:view_a_book"),
    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    DRIVER_REGISTER("driver:register"),
    DRIVER_VERIFY("driver:verify"),
    DRIVER_LOGIN("driver:login"),
    DRIVER_RESET_PASSWORD_MAIL("driver:reset_password_mail"),
    DRIVER_RESET_PASSWORD("driver:reset_password"),
    DRIVER_REFRESH_TOKEN("driver:refresh_token"),
    DRIVER_VIEW_ALL_BOOKS("driver:view_all_books"),
    DRIVER_VIEW_A_BOOK("driver:view_a_book"),
    ;

    @Getter
    private final String permission;
}
