package com.fastcampus.blog.dto.request.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class LoginRequest(
   @field: NotEmpty(message = "cannot be blank")
   val username: String,
   @field: NotBlank(message = "password cannot be blank")
   val password: String)