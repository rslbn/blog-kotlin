package com.fastcampus.blog.repository

import com.fastcampus.blog.model.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository: JpaRepository<Article, Long> {

   fun findByIsDeleted(isDeleted: Boolean): List<Article>
   fun findBySlugAndIsDeleted(slug: String, isDeleted: Boolean): Article?
   fun findBySlug(slug: String): Article?
}