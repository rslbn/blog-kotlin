package com.fastcampus.blog.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateCommentRequest(
   @field: NotBlank(message = "{validate.content.not.blank}")
   @field: Size(message = "validate.content.size", min = 20, max = 255)
   val content: String?
)