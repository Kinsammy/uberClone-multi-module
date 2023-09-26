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
        public static final String FEATURE = "features";
        public static final String ACCESS_TOKEN = "access_tokens";
        public static final String REFRESH_TOKEN = "refresh_tokens";

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

    public static abstract class FeatureType {
        public static final Integer APP = 2;
    }

    public static abstract class Role {
        public static final String DEFAULT_ROLE_BASIC = "BASIC";

        public static final String DEFAULT_ROLE_ADMIN = "ADMIN";
    }

    public static abstract class User {
        public static final Long SYSTEM_ID = 0L;

        public static final Long ANONYMOUS_ID = -1L;
    }

    public static abstract class TokenAudience {
        public static final String ACCESS_TOKEN ="ACC";
        public static final String REFRESH_TOKEN = "REF";
    }

    public static abstract class RegexPatterns {
        public static final String PHONE_NUMBER_PATTERN = "0[789][01][0-9]{8}";
    }

}
