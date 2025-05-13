package com.fastcampus.blog.service

import com.fastcampus.blog.dto.request.CreateArticleRequest
import com.fastcampus.blog.dto.request.UpdateArticleRequest
import com.fastcampus.blog.dto.response.ArticleDTO

interface ArticleService {
   fun findAll(): List<ArticleDTO>
   fun findBySlug(slug: String): ArticleDTO
   fun create(request: CreateArticleRequest): ArticleDTO
   fun update(slug: String, request: UpdateArticleRequest): ArticleDTO
   fun publish(slug: String): ArticleDTO
   fun delete(slug: String)
}