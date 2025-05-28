CREATE TABLE categories(
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE articles_categories(
    category_id INT,
    article_id BIGINT,
    PRIMARY KEY(category_id, article_id)
);