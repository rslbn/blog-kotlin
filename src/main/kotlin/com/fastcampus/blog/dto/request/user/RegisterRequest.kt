package com.fastcampus.blog.dto.request.user

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

// TODO: validate for case -> "______", "@@@@@@@@@@" (?)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RegisterRequest(
   @field:NotEmpty(message = "{validate.not.empty}")
   @field:Pattern(regexp = "^[\\w.@]+$", message = "{validate.username.pattern}")
   @field:Size(message = "{validate.size}", min = 3, max = 50)
   val username: String?,

   // pattern to validate firstname and lastname
   @field:NotEmpty(message = "{validate.not.empty}")
   @field:Size(message = "{validate.size}", min = 2, max = 50)
   val firstname: String?,

   @field:NotEmpty(message = "{validate.not.empty}")
   @field:Size(message = "{validate.size}", min = 2, max = 50)
   val lastname: String?,

   @field:NotEmpty(message = "{validate.not.empty}")
   @field:Email(message = "{validate.email}")
   val email: String?,

   @field:Size(message = "{validate.size}", min = 8, max = 100)
   @field:Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9\\s])(?!.*\\s).{8,}\$", message = "{validate.password.pattern}")
   val password: String?,

   @field:Size(message = "{validate.size}", min = 8, max = 100)
   val confirmationPassword: String?
)