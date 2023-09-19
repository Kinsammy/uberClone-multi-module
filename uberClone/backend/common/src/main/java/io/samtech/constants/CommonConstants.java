package io.samtech.constants;

public abstract class CommonConstants {
    public static abstract class EntityName {
        public static final String USER = "user";
        public static final String PROFILE = "profile";
        public static final String ROLE = "role";
        public static final String AUTHORITY = "authority";
        public static final String USER_ROLE = "user_roles";
        public static final String ROLE_AUTHORITY = "role_authorities";
        public static final String GROUP = "group";
        public static final String GROUP_MEMBER = "group_members";

    }

    public static abstract class ProfileType{
        public static final String BASIC = "basic";
    }

    public static abstract class EntityStatus {
        public static final Integer ACTIVE = 1;

        public static final Integer INACTIVE = 0;

        public static final Integer LOCKED = 0;

        public static final Integer UNLOCKED = 1;

        public static final Integer DELETED = 9;

        public static final Integer ENABLED = 1;

        public static final Integer DISABLED = 0;

        public static final Integer VERIFIED = 1;

        public static final Integer UNVERIFIED = 0;
    }

}
