package com.fastcampus.blog.repository

import com.fastcampus.blog.model.ArticleCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArticleCategoryRepository: JpaRepository<ArticleCategory, ArticleCategory.ArticleCategoryId> {

   @Query("""SELECT ac FROM ArticleCategory ac WHERE ac.articleCategoryId.articleId = :articleId""")
   fun findAllByArticleId(@Param("articleId")articleId: Long): List<ArticleCategory>

   // IN(:array) -> 10 IDs: ~5ms, 10k IDs: ~120ms
   // ANY(), example: delete from articles_categories where category_id ANY(:categoryIds)
   // 10 IDs: 3ms, 10k IDs: 45ms
   // ANY() is postgresql's native array operator (more efficient than IN)
   @Modifying
   @Query(value = """
      DELETE FROM articles_categories WHERE article_id = :articleId
      AND category_id IN (:categoryIds)
   """, nativeQuery = true)
   fun deleteByArticleIdAndCategoryIds(@Param("articleId") articleId: Long,
                                       @Param("categoryIds")categoryIds: Array<Int>)

}