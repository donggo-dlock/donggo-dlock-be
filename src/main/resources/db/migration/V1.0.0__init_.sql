CREATE TABLE test_schema.users
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    email              VARCHAR(255)          NULL,
    nickname           VARCHAR(255)          NULL,
    address            VARCHAR(255)          NULL,
    certification_code VARCHAR(255)          NULL,
    status             VARCHAR(255)          NULL,
    last_login_at      BIGINT                NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE test_schema.posts
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    content     VARCHAR(255)          NULL,
    created_at  BIGINT                NULL,
    modified_at BIGINT                NULL,
    user_id     BIGINT                NULL,
    CONSTRAINT pk_posts PRIMARY KEY (id)
);

ALTER TABLE test_schema.posts
    ADD CONSTRAINT FK_POSTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);