package com.fastcampus.blog.dto.request.article

data class CreateArticleRequest (
   val title: String,
   val content: String,
   val categoryIds: List<Int>
)