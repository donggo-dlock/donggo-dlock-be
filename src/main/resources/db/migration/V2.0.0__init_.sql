CREATE TABLE foods
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    name             VARCHAR(50)           NULL,
    user_information VARCHAR(50)           NULL,
    password         VARCHAR(4)            NULL,
    content          VARCHAR(255)          NULL,
    days_before_test INT                   NULL,
    main_ingredient  VARCHAR(50)           NULL,
    views            INT                   NULL,
    likes            INT                   NULL,
    dislikes         INT                   NULL,
    created_at       BIGINT                NULL,
    status      enum('ACTIVE', 'INACTIVE'),
    CONSTRAINT pk_foods PRIMARY KEY (id)
);

CREATE TABLE reviews
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    name             VARCHAR(50)           NULL,
    user_information VARCHAR(50)           NULL,
    password         VARCHAR(4)            NULL,
    content          VARCHAR(255)          NULL,
    views            INT                   NULL,
    likes            INT                   NULL,
    dislikes         INT                   NULL,
    created_at       BIGINT                NULL,
    gender           CHAR                  NULL,
    age              INT                   NULL,
    title            VARCHAR(255)          NULL,
    sleep_flag       BIT(1)                NULL,
    result           VARCHAR(255)          NULL,
    status      enum('ACTIVE', 'INACTIVE'),
    CONSTRAINT pk_reviews PRIMARY KEY (id)
);


CREATE TABLE comments
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    name             VARCHAR(50)           NULL,
    user_information VARCHAR(50)           NULL,
    password         VARCHAR(4)            NULL,
    content          VARCHAR(255)          NULL,
    status     enum('ACTIVE', 'INACTIVE'),
    created_at       BIGINT                NULL,
    reference_type   enum('FOOD', 'REVIEW'),
    food_id          BIGINT                NULL,
    review_id        BIGINT                NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_FOOD FOREIGN KEY (food_id) REFERENCES foods (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_REVIEW FOREIGN KEY (review_id) REFERENCES reviews (id);

CREATE TABLE reports
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    er_information VARCHAR(50)           NULL,
    ed_information VARCHAR(50)           NULL,
    status         enum('RECEPTION', 'REJECTED', 'APPROVED'),
    created_at     BIGINT                NULL,
    reference_type enum('COMMENT', 'FOOD', 'REVIEW'),
    comment_id     BIGINT                NULL,
    food_id        BIGINT                NULL,
    review_id      BIGINT                NULL,
    CONSTRAINT pk_reports PRIMARY KEY (id)
);

ALTER TABLE reports
    ADD CONSTRAINT FK_REPORTS_ON_COMMENT FOREIGN KEY (comment_id) REFERENCES comments (id);

ALTER TABLE reports
    ADD CONSTRAINT FK_REPORTS_ON_FOOD FOREIGN KEY (food_id) REFERENCES foods (id);

ALTER TABLE reports
    ADD CONSTRAINT FK_REPORTS_ON_REVIEW FOREIGN KEY (review_id) REFERENCES reviews (id);