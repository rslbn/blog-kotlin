package com.fastcampus.blog.dto.request.article

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateArticleRequest (
   @field: NotBlank(message = "{validate.title.not.blank}")
   @field: Size(message = "{validate.title.size}", min = 5, max = 100)
   val title: String,
   @field: NotBlank(message = "{validate.content.not.blank")
   @field: Size(message = "{validate.content.size}", min = 50, max = 5000)
   val content: String,
   @field: Size(message = "{validate.categoryids.size}", min = 1)
   val categoryIds: List<Int>
)