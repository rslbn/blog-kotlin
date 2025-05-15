package com.fastcampus.blog.dto.response

import java.time.LocalDateTime

data class UserResponse(
   val userId: Long,
   val username: String,
   val fullname: String,
   val email: String,
   val createdAt: LocalDateTime?,
   val updatedAt: LocalDateTime?
)