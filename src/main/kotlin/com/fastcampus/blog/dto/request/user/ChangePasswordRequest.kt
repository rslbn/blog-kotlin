package com.fastcampus.blog.dto.request.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class ChangePasswordRequest(
   var username: String? = null,
   @field:NotBlank(message = "New password is required!")
   @field:Size(message = "New password is required!", min = 8, max = 100)
   @field:Pattern(message = "Must follow this pattern", regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9\\s])(?!.*\\s).{8,}\$")
   val newPassword: String?,
   @field:NotEmpty(message = "Confirmation password is required")
   val confirmationPassword: String?
)