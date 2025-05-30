package com.fastcampus.blog.dto.response

import java.time.LocalDateTime

data class CommentResponse(
   val articleId: Long,
   val userId: Long,
   val commentId: Long,
   val content: String,
   val createdAt: LocalDateTime,
   val updatedAt: LocalDateTime
)
