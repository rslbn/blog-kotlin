package com.fastcampus.blog.service

import com.fastcampus.blog.dto.request.CreateCommentRequest
import com.fastcampus.blog.dto.response.CommentResponse

interface CommentService {

   fun findAll(slug: String): List<CommentResponse>
   fun findById(slug: String, commentId: Long): CommentResponse
   fun create(slug: String, request: CreateCommentRequest): CommentResponse
   fun delete(slug: String, commentId: Long)
}