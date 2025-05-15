package com.fastcampus.blog.service

import com.fastcampus.blog.dto.request.CreateArticleRequest
import com.fastcampus.blog.dto.request.UpdateArticleRequest
import com.fastcampus.blog.dto.response.ArticleResponse

interface ArticleService {
   fun findAll(): List<ArticleResponse>
   fun findBySlug(slug: String): ArticleResponse
   fun create(request: CreateArticleRequest): ArticleResponse
   fun update(slug: String, request: UpdateArticleRequest): ArticleResponse
   fun publish(slug: String): ArticleResponse
   fun delete(slug: String)
}