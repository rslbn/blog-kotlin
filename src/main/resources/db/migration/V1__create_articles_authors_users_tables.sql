CREATE TABLE ARTICLES (
	ARTICLE_ID BIGSERIAL PRIMARY KEY,
	AUTHOR_ID BIGINT NOT NULL,
	TITLE VARCHAR(100) NOT NULL,
	SLUG VARCHAR(100) NOT NULL UNIQUE,
	DESCRIPTION VARCHAR(255),
	"content" TEXT,
	PUBLISHED_AT TIMESTAMP,
	CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	UPDATED_AT TIMESTAMP,
	IS_DELETED BOOLEAN DEFAULT FALSE
);

CREATE TABLE AUTHORS (
	AUTHOR_ID BIGSERIAL PRIMARY KEY,
	USER_ID BIGINT NOT NULL UNIQUE,
	BIO VARCHAR(255),
	"ALIAS" VARCHAR(100) UNIQUE,
	CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	UPDATED_AT TIMESTAMP,
	IS_DELETED BOOLEAN DEFAULT FALSE
);

CREATE TABLE USERS (
	USER_ID BIGSERIAL PRIMARY KEY,
	USERNAME VARCHAR(50) NOT NULL UNIQUE,
	"PASSWORD" VARCHAR(255) NOT NULL,
	EMAIL VARCHAR(100) NOT NULL UNIQUE,
	CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	UPDATED_AT TIMESTAMP,
	IS_DELETED BOOLEAN DEFAULT FALSE
);

ALTER TABLE AUTHORS ADD CONSTRAINT FK_USERS_AUTHORS
FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID);

ALTER TABLE ARTICLES ADD CONSTRAINT FK_AUTHORS_ARTICLES
FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(AUTHOR_ID);