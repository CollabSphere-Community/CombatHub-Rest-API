CREATE TABLE users (
    id BIGINT AUTO_INCREMENT UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(25) NOT NULL,
    registered_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    confirmed BIT(1) DEFAULT 0,

    PRIMARY KEY (id)
);

CREATE TABLE user_verification_code (
    id BIGINT AUTO_INCREMENT UNIQUE,
    code VARCHAR(255) NOT NULL UNIQUE,
    user BIGINT NOT NULL,
    expires_at DATETIME NOT NULL,
    valid BIT(1) DEFAULT 1,

    PRIMARY KEY (id),
    FOREIGN KEY (user) REFERENCES users(id)
);