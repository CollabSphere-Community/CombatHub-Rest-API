CREATE TABLE athletes (
    id BIGINT AUTO_INCREMENT UNIQUE,
    user BIGINT UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    modality VARCHAR(25) NOT NULL,
    avatar VARCHAR(255),
    registered_at DATETIME,

    PRIMARY KEY (id),
    FOREIGN KEY (user) REFERENCES users(id)
);

CREATE TABLE organizations (
    id BIGINT AUTO_INCREMENT UNIQUE,
    user BIGINT UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    avatar VARCHAR(255),
    registered_at DATETIME,

    PRIMARY KEY (id),
    FOREIGN KEY (user) REFERENCES users(id)
);

CREATE TABLE gyms (
    id BIGINT AUTO_INCREMENT UNIQUE,
    user BIGINT UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(25) NOT NULL,
    avatar VARCHAR(255),
    registered_at DATETIME,

    PRIMARY KEY (id),
    FOREIGN KEY (user) REFERENCES users(id)
);