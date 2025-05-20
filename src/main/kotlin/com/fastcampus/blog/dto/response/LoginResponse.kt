package com.fastcampus.blog.dto.response

data class LoginResponse (
   val username: String,
   val type: String,
   val token: String
)