package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.MessageSourceUtil
import com.fastcampus.blog.common.util.mapToArticleDTO
import com.fastcampus.blog.common.util.toSlug
import com.fastcampus.blog.dto.request.CreateArticleRequest
import com.fastcampus.blog.dto.request.UpdateArticleRequest
import com.fastcampus.blog.dto.response.ArticleResponse
import com.fastcampus.blog.model.Article
import com.fastcampus.blog.repository.ArticleRepository
import com.fastcampus.blog.service.ArticleService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArticleServiceImpl(
   private val articleRepository: ArticleRepository,
   private val messageSourceUtil: MessageSourceUtil
) : ArticleService {

   override fun findAll(): List<ArticleResponse> {
      return articleRepository.findByIsDeleted(false)
         .map { it.mapToArticleDTO() }
   }

   override fun findBySlug(slug: String): ArticleResponse {
      val result = articleRepository.findBySlugAndIsDeleted(slug, false)
      return result?.mapToArticleDTO()
         ?: throw RuntimeException(messageSourceUtil.getMessage("error.not.found", "Article"))
   }

   override fun create(request: CreateArticleRequest): ArticleResponse {
      var article = Article(
         title = request.title,
         content = request.content
      )
      article.authorId = 1
      article = articleRepository.save(article)
      return article.mapToArticleDTO()
   }

   override fun update(slug: String, request: UpdateArticleRequest): ArticleResponse =
      // TODO: coba item.copy() untuk tidak langsung merubah state item
      articleRepository.findBySlugAndIsDeleted(slug, false)?.let { item ->
         with(item) {
            title = request.title
            content = request.content
            item.slug = request.title.toSlug()
         }
         articleRepository.save(item).mapToArticleDTO()
      } ?: throw RuntimeException(messageSourceUtil.getMessage("error.not.found", "Article"))

   override fun publish(slug: String): ArticleResponse =
      articleRepository.findBySlugAndIsDeleted(slug, false)?.let { article ->
         if (article.isPublished)
            throw RuntimeException(messageSourceUtil.getMessage("error.article.already.published"))
         article.publishedAt = LocalDateTime.now()
         article.isPublished = true
         articleRepository.save(article).mapToArticleDTO()
      }  ?: throw RuntimeException(messageSourceUtil.getMessage("error.not.found", "Article"))

   override fun delete(slug: String) {
     articleRepository.findBySlugAndIsDeleted(slug, false)?.let { article ->
        article.isDeleted = true
        articleRepository.save(article)
        return
     } ?: throw RuntimeException(messageSourceUtil.getMessage("error.not.found", "article"))
   }
}