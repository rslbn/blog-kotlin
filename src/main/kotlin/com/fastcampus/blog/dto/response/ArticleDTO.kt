package com.fastcampus.blog.dto.response

import java.time.LocalDateTime

data class ArticleDTO(
   val articleId: Long,
   val authorId: Long,
   val title: String,
   val slug: String,
   val content: String,
   val publishedAt: LocalDateTime? = null,
   val createdAt: LocalDateTime,
   val updatedAt: LocalDateTime? = null,
   val isPublished: Boolean
)