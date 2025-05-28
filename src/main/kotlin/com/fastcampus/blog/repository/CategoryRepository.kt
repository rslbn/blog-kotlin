package com.fastcampus.blog.repository

import com.fastcampus.blog.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CategoryRepository: JpaRepository<Category, Int> {

   @Query(value = """
      SELECT c.* from categories c
      JOIN articles_categories ac on ac.category_id = c.category_id
      JOIN articles a on a.article_id = ac.article_id
      WHERE ac.article_id = :articleId
   """, nativeQuery = true)
   fun findByArticleId(articleId: Long): Iterable<Category>
}