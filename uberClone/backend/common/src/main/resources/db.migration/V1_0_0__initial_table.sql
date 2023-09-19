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


CREATE TABLE IF NOT EXISTS authorities (
                                id BIGSERIAL PRIMARY KEY,
                                created_by VARCHAR(255),
                                created_date TIMESTAMP NULL,
                                last_modified_by VARCHAR(255),
                                description VARCHAR(500) null,
                                name  VARCHAR(100) not null,
                                status int(1) not null,

)
