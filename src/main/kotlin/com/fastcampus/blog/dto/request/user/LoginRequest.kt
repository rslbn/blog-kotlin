package com.fastcampus.blog.dto.request.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class LoginRequest(
   @field: NotEmpty(message = "{validate.username.not.empty}")
   val username: String,
   @field: NotBlank(message = "{validate.password.not.blank}")
   val password: String)