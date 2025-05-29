CREATE TABLE comments(
    comment_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_articles_comments_article_id FOREIGN KEY(article_id) REFERENCES articles(article_id),
    CONSTRAINT fk_users_comments_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);