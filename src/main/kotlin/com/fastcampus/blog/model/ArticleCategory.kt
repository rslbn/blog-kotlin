package com.fastcampus.blog.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "articles_categories", schema = "public")
class ArticleCategory(
   @EmbeddedId
   var articleCategoryId: ArticleCategoryId
) {
   @Embeddable
   data class ArticleCategoryId(
      @Column(name = "article_id")
      var articleId: Long,
      @Column(name = "category_id")
      var categoryId: Int
   )
}