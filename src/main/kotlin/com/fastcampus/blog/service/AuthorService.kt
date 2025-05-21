package com.fastcampus.blog.service

import com.fastcampus.blog.dto.request.author.RegisterAuthorRequest
import com.fastcampus.blog.dto.response.AuthorResponse

interface AuthorService {
   fun findAll(): List<AuthorResponse>
   fun findById(authorId: Long): AuthorResponse
   fun findByUserId(userId: Long): AuthorResponse
   fun register(request: RegisterAuthorRequest): AuthorResponse
}