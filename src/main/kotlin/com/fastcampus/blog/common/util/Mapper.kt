package com.fastcampus.blog.common.util

import com.fastcampus.blog.dto.response.ArticleDTO
import com.fastcampus.blog.dto.response.AuthorDTO
import com.fastcampus.blog.dto.response.UserDTO
import com.fastcampus.blog.entity.Article
import com.fastcampus.blog.entity.Author
import com.fastcampus.blog.entity.User

fun Article.mapToArticleDTO() = ArticleDTO(
   articleId!!, authorId!!, title,
   slug, content!!,
   publishedAt, createdAt!!, isPublished = isPublished
)

fun Author.mapToAuthorDTO() = AuthorDTO(
   authorId, userId, bio!!,
   alias!!, createdAt, updatedAt,
   false
)

fun User.mapToUserDTO() = UserDTO(
   userId, username, email!!, createdAt, updatedAt
)