package com.fastcampus.blog.common.util

import com.fastcampus.blog.dto.response.ArticleResponse
import com.fastcampus.blog.dto.response.AuthorResponse
import com.fastcampus.blog.dto.response.CommentResponse
import com.fastcampus.blog.dto.response.UserResponse
import com.fastcampus.blog.model.Article
import com.fastcampus.blog.model.Author
import com.fastcampus.blog.model.Comment
import com.fastcampus.blog.model.User

fun Article.mapToArticleResponse() = ArticleResponse(
   articleId!!, authorId!!, title,
   slug, content!!,
   publishedAt, createdAt!!, updatedAt,isPublished = isPublished
)

fun Author.mapToAuthorResponse() = AuthorResponse(
   authorId!!, userId!!, bio!!,
   alias!!, createdAt!!, updatedAt,
   false
)

fun Comment.mapToCommentResponse() = CommentResponse(
   articleId!!, userId!!, commentId!!, content!!, createdAt!!, updatedAt!!
)

fun User.mapToUserResponse() = UserResponse(
   userId!!, username, getFullname(), email!!, createdAt, updatedAt
)