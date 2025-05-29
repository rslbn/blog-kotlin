ALTER TABLE articles_categories
ADD CONSTRAINT fk_articles_categories_article_id FOREIGN KEY (article_id) REFERENCES articles(article_id),
ADD CONSTRAINT fk_articles_categories_category_id FOREIGN KEY (category_id) REFERENCES categories(category_id);