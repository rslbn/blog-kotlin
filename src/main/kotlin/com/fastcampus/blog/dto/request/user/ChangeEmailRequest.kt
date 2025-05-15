package com.fastcampus.blog.dto.request.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class ChangeEmailRequest(
   var username: String? = null,
   @field:NotEmpty(message = "Email is required")
   @field:Email(message = "Must followed the format")
   val email: String?,

   @field:NotEmpty(message = "Confirmation password cannot be empty!")
   val confirmationPassword: String?
)
