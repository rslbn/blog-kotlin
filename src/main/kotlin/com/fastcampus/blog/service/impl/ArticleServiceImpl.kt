package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.MessageSourceUtil
import com.fastcampus.blog.common.error.ResourceNotFoundException
import com.fastcampus.blog.common.util.mapToArticleResponse
import com.fastcampus.blog.common.util.toSlug
import com.fastcampus.blog.dto.request.article.CreateArticleRequest
import com.fastcampus.blog.dto.request.article.UpdateArticleRequest
import com.fastcampus.blog.dto.response.ArticleResponse
import com.fastcampus.blog.model.Article
import com.fastcampus.blog.model.UserInfo
import com.fastcampus.blog.repository.ArticleRepository
import com.fastcampus.blog.repository.AuthorRepository
import com.fastcampus.blog.service.ArticleService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArticleServiceImpl(
   private val articleRepository: ArticleRepository,
   private val authorRepository: AuthorRepository,
   private val messageSourceUtil: MessageSourceUtil
) : ArticleService {

   // TODO: Add pagination, filter (search by title), and sort by
   override fun findAll(): List<ArticleResponse> {
      return articleRepository.findByIsDeleted(false)
         .map { it.mapToArticleResponse() }
   }

   override fun findBySlug(slug: String): ArticleResponse {
      val result = articleRepository.findBySlugAndIsDeleted(slug, false)
      return result?.mapToArticleResponse()
         ?: throw RuntimeException(messageSourceUtil.getMessage("error.not.found", "Article"))
   }

   // TODO: Verify if the user has AUTHOR role.
   override fun create(request: CreateArticleRequest): ArticleResponse {
      val user = SecurityContextHolder.getContext().authentication.principal as UserInfo

      if (!user.roles.map { it.name }.contains("AUTHOR")) {
         throw RuntimeException("User must have AUTHOR role")
      }
      var article = Article(
         title = request.title,
         content = request.content
      )
      article.authorId = 1
      article = articleRepository.save(article)
      return article.mapToArticleResponse()
   }

   // TODO: Verify the owner of the article
   // TODO: Ask how do tell that nothing is changed?
   override fun update(slug: String, request: UpdateArticleRequest): ArticleResponse =
      articleRepository.findBySlugAndIsDeleted(slug, false)?.let { item ->
         if (request.title.isBlank() && request.content.isBlank()) {
            item.mapToArticleResponse()
         } else {
            with(item) {
               if (!request.title.isBlank()) {
                  title = request.title
                  item.slug = request.title.toSlug()
               }
               if (!request.content.isBlank()) content = request.content
            }
            articleRepository.save(item).mapToArticleResponse()
         }
      } ?: throw RuntimeException(messageSourceUtil.getMessage("error.not.found", "Article"))

   // TODO: Verify the owner of the article
   override fun publish(slug: String): ArticleResponse =
      articleRepository.findBySlugAndIsDeleted(slug, false)?.let { article ->
         val user = SecurityContextHolder.getContext().authentication.principal as UserInfo
         val author = authorRepository.findByUserId(user.user.userId!!) ?: throw ResourceNotFoundException("Author not found!")
         if (article.authorId != author.authorId) {
            throw ResourceNotFoundException("User does not own this article!")
         }
         if (article.isPublished)
            throw RuntimeException(messageSourceUtil.getMessage("error.article.already.published"))
         article.publishedAt = LocalDateTime.now()
         article.isPublished = true
         articleRepository.save(article).mapToArticleResponse()
      }  ?: throw RuntimeException(messageSourceUtil.getMessage("error.not.found", "Article"))

   // TODO: Verify the owner of the article or the operation done by SUPERUSER or ADMIN
   override fun delete(slug: String) {
      val savedArticle = articleRepository.findBySlugAndIsDeleted(slug, false)
         ?: throw ResourceNotFoundException("Article not found with slug, ${slug}!")
      savedArticle.isDeleted = true
      articleRepository.save(savedArticle)
   }
}