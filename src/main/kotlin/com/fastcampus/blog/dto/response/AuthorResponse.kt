package com.fastcampus.blog.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AuthorResponse (
   val authorId: Long,
   val userId: Long,
   val bio: String,
   val alias: String,
   val createdAt: LocalDateTime,
   val updatedAt: LocalDateTime? = null,
   val isDeleted: Boolean
)