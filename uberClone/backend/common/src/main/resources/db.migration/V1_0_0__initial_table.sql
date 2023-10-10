CREATE TABLE IF NOT EXISTS groups (
                                      id BIGSERIAL PRIMARY KEY,
                                      created_by VARCHAR(255),
                                      created_date TIMESTAMP NULL,
                                      "last_modified_by" VARCHAR(255),
                                      "last_modified_date" TIMESTAMP NULL,
                                      description VARCHAR(500),
                                      name VARCHAR(100),
                                      status INT NOT NULL
);

CREATE TABLE IF NOT EXISTS features (
                                        id BIGSERIAL PRIMARY KEY,
                                        created_by VARCHAR(255),
                                        created_date TIMESTAMP NULL,
                                        "last_modified_by" VARCHAR(255),
                                        "last_modified_date" TIMESTAMP NULL,
                                        name VARCHAR(255) NOT NULL,
                                        type INT DEFAULT 2 NOT NULL,
                                        description TEXT NULL
);


CREATE UNIQUE INDEX IF NOT EXISTS features_id_uindex ON features (id);
CREATE UNIQUE INDEX IF NOT EXISTS features_name_uindex ON features (name);

CREATE TABLE IF NOT EXISTS authorities (
                                           id BIGSERIAL PRIMARY KEY,
                                           created_by VARCHAR(255),
                                           created_date TIMESTAMP NULL,
                                           "last_modified_by" VARCHAR(255),
                                           "last_modified_date" TIMESTAMP NULL,
                                           description VARCHAR(500) NULL,
                                           name VARCHAR(100) NOT NULL,
                                           status INT NOT NULL,
                                           feature_id BIGINT NOT NULL,
                                           CONSTRAINT authorities_name_uindex UNIQUE (name),
                                           CONSTRAINT authorities_feature_id_fk FOREIGN KEY (feature_id) REFERENCES features(id)
);

CREATE TABLE IF NOT EXISTS roles (
                                     id BIGSERIAL PRIMARY KEY,
                                     created_by VARCHAR(255),
                                     created_date TIMESTAMP NULL,
                                     "last_modified_by" VARCHAR(255),
                                     "last_modified_date" TIMESTAMP NULL,
                                     description VARCHAR(500) NULL,
                                     name VARCHAR(100) NOT NULL,
                                     status INT NOT NULL,
                                     CONSTRAINT uk_roles_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS role_authorities (
                                                role_id BIGINT NOT NULL,
                                                authority_id BIGINT NOT NULL,
                                                CONSTRAINT roles_authorities_pk UNIQUE (role_id, authority_id),
                                                CONSTRAINT roles_authorities_authorities_id_pk FOREIGN KEY (authority_id) References authorities (id),
                                                CONSTRAINT role_authorities_roles_id_fk FOREIGN KEY (role_id) REFERENCES roles (id)
);

create index role_authorities_role_id_authority_id_index on role_authorities (role_id, authority_id);


CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT  PRIMARY KEY,
                                     created_by VARCHAR(255),
                                     created_date TIMESTAMP NULL,
                                     "last_modified_by" VARCHAR(255),
                                     "last_modified_date" TIMESTAMP NULL,
                                     birthdate DATE NULL,
                                     email VARCHAR(255) NOT NULL,
                                     email_verified INT NULL,
                                     enabled INT NULL,
                                     locked INT NULL,
                                     family_name VARCHAR(255) NULL,
                                     gender INT NULL,
                                     given_name VARCHAR(255) NULL,
                                     middle_name VARCHAR(255) NULL,
                                     NAME VARCHAR(255) NULL,
                                     PASSWORD VARCHAR(255) NULL,
                                     raw_password VARCHAR(255) NULL,
                                     phone_number VARCHAR(255) NULL,
                                     phone_number_verified INT NULL,
                                     preferred_username VARCHAR(255) NOT NULL,
                                     unsigned_name VARCHAR(255) NULL,
                                     username VARCHAR(255) NOT NULL,

                                     CONSTRAINT uk_users_username UNIQUE (username),
                                     CONSTRAINT uk_users_email UNIQUE (email),
                                     CONSTRAINT uk_users_phone_number UNIQUE (phone_number),
                                     CONSTRAINT users_preferred_username_uindex UNIQUE (preferred_username)
);
CREATE TABLE IF NOT EXISTS profiles (
                                        id BIGINT  PRIMARY KEY,
                                        created_by VARCHAR(255),
                                        created_date TIMESTAMP NULL,
                                        "last_modified_by" VARCHAR(255),
                                        "last_modified_date" TIMESTAMP NULL,
                                        user_id BIGINT NULL,
                                        type VARCHAR(255) NULL,
                                        is_activated INT NOT NULL,
                                        status INT NOT NULL,

                                        CONSTRAINT fk_profiles__users FOREIGN KEY (user_id) REFERENCES users (id)



);
CREATE TABLE IF NOT EXISTS group_members (
                                             user_id BIGINT NOT NULL,
                                             group_id BIGINT NOT NULL,
                                             PRIMARY KEY (user_id, group_id),
                                             CONSTRAINT fk_group_members__groups FOREIGN KEY (group_id) REFERENCES groups (id),
                                             CONSTRAINT fk_group_members__users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role_id BIGINT NOT NULL,
                                          PRIMARY KEY (user_id, role_id),
                                          CONSTRAINT fk_user_roles__roles FOREIGN KEY (role_id) REFERENCES roles (id),
                                          CONSTRAINT fk_user_roles__users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS user_profile(
                                           user_id BIGINT NOT NULL,
                                           profile_id BIGINT NOT NULL,
                                           PRIMARY KEY (user_id, profile_id),
                                           CONSTRAINT fk_user_profile__profiles FOREIGN KEY (profile_id) REFERENCES profiles (id),
                                           CONSTRAINT fk_user_profile__users FOREIGN KEY (user_id) REFERENCES users (id)


);


CREATE TABLE IF NOT EXISTS access_tokens (
                                        id VARCHAR(255) NOT NULL PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        expires_at TIMESTAMP NULL,
                                        status INT NOT NULL,
                                        CONSTRAINT fk_access_tokens_users FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS refresh_tokens (
                                             id VARCHAR(255) NOT NULL PRIMARY KEY,
                                             access_token_id VARCHAR (255) NOT NULL,
                                             user_id BIGINT NOT NULL,
                                             expires_at TIMESTAMP NULL,
                                             status INT NOT NULL,
                                             CONSTRAINT fk_refresh_tokens__access_tokens FOREIGN KEY (access_token_id) REFERENCES access_tokens (id)
);

CREATE TABLE IF NOT EXISTS user_profile(
                                           user_id BIGINT NOT NULL,
                                           profile_id BIGINT NOT NULL,
                                           PRIMARY KEY (user_id, profile_id),
                                           CONSTRAINT fk_user_profile__profiles FOREIGN KEY (profile_id) REFERENCES profiles (id),
                                           CONSTRAINT fk_user_profile__users FOREIGN KEY (user_id) REFERENCES users (id)


);

CREATE TABLE IF NOT EXISTS tokens(
                                     id BIGINT  PRIMARY KEY,
                                     created_by VARCHAR(255),
                                     created_date TIMESTAMP NULL,
                                     "last_modified_by" VARCHAR(255),
                                     "last_modified_date" TIMESTAMP NULL,
                                     user_id BIGINT NULL,
                                     token_type VARCHAR(10),

)

--
--
-- CREATE TABLE IF NOT EXISTS addresses (
--                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
--                                          created_by VARCHAR(255) NULL,
--                                          created_date DATETIME(6) NULL,
--                                          last_modified_by VARCHAR(255) NULL,
--                                          last_modified_date DATETIME(6) NULL,
--                                          street_name VARCHAR(255) NOT NULL,
--                                          house_no VARCHAR(255) NULL,
--                                          landmark VARCHAR(255) NOT NULL,
--                                          city VARCHAR(255) NOT NULL,
--                                          state VARCHAR(255) NULL,
--                                          country VARCHAR(255) NULL,
--                                          latitude VARCHAR(255) NULL,
--                                          longitude VARCHAR(255) NULL,
--                                          address_type VARCHAR(255) NULL
--
-- );

