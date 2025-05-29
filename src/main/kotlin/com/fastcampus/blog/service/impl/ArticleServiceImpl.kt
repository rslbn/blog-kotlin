package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.ErrorMessageKeys.ARTICLE_ALREADY_PUBLISHED
import com.fastcampus.blog.common.ErrorMessageKeys.ARTICLE_NOT_FOUND_SLUG
import com.fastcampus.blog.common.ErrorMessageKeys.INVALID_ARTICLE_OWNERSHIP
import com.fastcampus.blog.common.ErrorMessageKeys.MUST_HAVE_ROLE
import com.fastcampus.blog.common.ErrorMessageKeys.NOT_FOUND
import com.fastcampus.blog.common.MessageSourceUtil
import com.fastcampus.blog.common.error.ResourceNotFoundException
import com.fastcampus.blog.common.util.mapToArticleResponse
import com.fastcampus.blog.common.util.toSlug
import com.fastcampus.blog.dto.request.article.CreateArticleRequest
import com.fastcampus.blog.dto.request.article.UpdateArticleRequest
import com.fastcampus.blog.dto.response.ArticleResponse
import com.fastcampus.blog.model.Article
import com.fastcampus.blog.model.ArticleCategory
import com.fastcampus.blog.model.UserInfo
import com.fastcampus.blog.repository.ArticleCategoryRepository
import com.fastcampus.blog.repository.ArticleRepository
import com.fastcampus.blog.repository.AuthorRepository
import com.fastcampus.blog.repository.CategoryRepository
import com.fastcampus.blog.service.ArticleService
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArticleServiceImpl(
   private val articleRepository: ArticleRepository,
   private val authorRepository: AuthorRepository,
   private val categoryRepository: CategoryRepository,
   private val articleCategoryRepository: ArticleCategoryRepository,
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
         ?: throw RuntimeException(messageSourceUtil.getMessage(NOT_FOUND, "Article"))
   }

   // TODO: verify if the categoryIds is not empty and category does exists. For create() and update()
   @Transactional
   override fun create(request: CreateArticleRequest): ArticleResponse {
      val user = SecurityContextHolder.getContext().authentication.principal as UserInfo
      if (!user.roles.map { it.name }.contains("AUTHOR")) {
         throw RuntimeException(messageSourceUtil.getMessage(MUST_HAVE_ROLE, "AUTHOR"))
      }

      val author = authorRepository.findByUserId(user.user.userId!!) ?: throw ResourceNotFoundException(
         messageSourceUtil.getMessage(NOT_FOUND, "Author")
      )

      var article = Article(
         title = request.title,
         content = request.content,
         authorId = author.authorId
      )
      article = articleRepository.save(article)

      if(request.categoryIds.isNotEmpty()) {
         val categories = categoryRepository.findAllById(request.categoryIds)
            .map { it.categoryId!! }
         if (categories.isNotEmpty()) {
            val articleCategories = createArticleCategories(article.articleId!!, categories)
            articleCategoryRepository.saveAll(articleCategories)
         }
      }
      return article.mapToArticleResponse()
   }

   // TODO: Ask how do tell that nothing is changed?
   @Transactional
   override fun update(slug: String, request: UpdateArticleRequest): ArticleResponse =
      articleRepository.findBySlugAndIsDeleted(slug, false)?.let { savedArticle ->
         if (request.title.isBlank() && request.content.isBlank() && !request.categoryIds.isNotEmpty()) {
            savedArticle.mapToArticleResponse()
         } else {
            with(savedArticle) {
               if (!request.title.isBlank() && request.title != savedArticle.title) {
                  title = request.title
                  savedArticle.slug = request.title.toSlug()
               }
               if (!request.content.isBlank()) content = request.content

               if (request.categoryIds.isNotEmpty()) {
                  println("${LocalDateTime.now()} [DEBUG] Processing the category")
                  val existingArticleCategories = articleCategoryRepository
                     .findAllByArticleId(savedArticle.articleId!!)
                  if (existingArticleCategories.isEmpty()) {
                     println("${LocalDateTime.now()} [DEBUG] existingArticleCategories is empty. Creating new list.")
                     val articleCategories = createArticleCategories(
                        savedArticle.articleId!!, request.categoryIds
                     )
                     articleCategoryRepository.saveAll(articleCategories)
                  } else {
                     println("${LocalDateTime.now()} [DEBUG] existingArticleCategories = $existingArticleCategories")
                     val existingCategoryIds = existingArticleCategories
                        .map { it.articleCategoryId.categoryId }
                        .toSet()

                     val categoryToDelete = existingCategoryIds
                        .filter { !request.categoryIds.contains(it) }
                        .toTypedArray()

                     println("${LocalDateTime.now()} [DEBUG] existingCategoryIds: ${existingCategoryIds.joinToString(", ")}")
                     println("${LocalDateTime.now()} [DEBUG] categoryToDelete: ${categoryToDelete.joinToString(", ")}")
                     println("${LocalDateTime.now()} [DEBUG] request.categoryIds: ${request.categoryIds.joinToString(", ")}")

                     /**
                      * Improved code, using kotlin's set operations ( '-' for differences)
                      * NOTE: existingCategoryIds and request.categoryIds need to be converted to Set first
                      * val newCategoryToAdd = request.categoryIds
                      *   .filter { categoryId -> !existingCategoryIds.contains(categoryId)}
                      */

                     val newCategoryIdsToAdd = request.categoryIds - existingCategoryIds
                        .toSet()
                     println("${LocalDateTime.now()} [DEBUG] newCategoryIdsToAdd: ${newCategoryIdsToAdd.joinToString(", ")}")

                     if (!categoryToDelete.isEmpty()) {
                        articleCategoryRepository
                           .deleteByArticleIdAndCategoryIds(
                              savedArticle.articleId!!,
                              categoryToDelete)
                     }
                     val categories = categoryRepository.findAllById(newCategoryIdsToAdd).map { it.categoryId!! }
                     val newArticleCategories =
                        createArticleCategories(savedArticle.articleId!!, categories)
                     articleCategoryRepository.saveAll(newArticleCategories)
                  }
               }
            }
            articleRepository.save(savedArticle).mapToArticleResponse()
         }
      } ?: throw RuntimeException(messageSourceUtil.getMessage(NOT_FOUND, "Article"))

   override fun publish(slug: String): ArticleResponse =
      articleRepository.findBySlugAndIsDeleted(slug, false)?.let { article ->
         val user = SecurityContextHolder.getContext().authentication.principal as UserInfo
         val author = authorRepository.findByUserId(user.user.userId!!) ?:
         throw ResourceNotFoundException(messageSourceUtil.getMessage(NOT_FOUND, "AUTHOR"))
         if (article.authorId != author.authorId) {
            throw ResourceNotFoundException(messageSourceUtil.getMessage(INVALID_ARTICLE_OWNERSHIP))
         }
         if (article.isPublished)
            throw RuntimeException(messageSourceUtil.getMessage(ARTICLE_ALREADY_PUBLISHED))
         article.publishedAt = LocalDateTime.now()
         article.isPublished = true
         articleRepository.save(article).mapToArticleResponse()
      }  ?: throw RuntimeException(messageSourceUtil.getMessage(NOT_FOUND, "Article"))

   override fun delete(slug: String) {
      val savedArticle = articleRepository.findBySlugAndIsDeleted(slug, false)
         ?: throw ResourceNotFoundException(messageSourceUtil.getMessage(ARTICLE_NOT_FOUND_SLUG, slug))
      savedArticle.isDeleted = true
      articleRepository.save(savedArticle)
   }

   private fun createArticleCategories(articleId: Long, categoryIds: List<Int>): List<ArticleCategory> =
      categoryIds.map { categoryId: Int ->
         ArticleCategory(
            ArticleCategory.ArticleCategoryId(articleId, categoryId)
         )
      }

}