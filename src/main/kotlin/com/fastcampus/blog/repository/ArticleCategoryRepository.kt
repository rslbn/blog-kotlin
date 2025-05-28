package com.fastcampus.blog.repository

import com.fastcampus.blog.model.ArticleCategory
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleCategoryRepository: JpaRepository<ArticleCategory, ArticleCategory.ArticleCategoryId> {
}