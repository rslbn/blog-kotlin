package com.fastcampus.blog.model

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
      var articleId: Long,
      var categoryId: Int
   )
}