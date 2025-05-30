package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.ErrorMessageKeys
import com.fastcampus.blog.common.MessageSourceUtil
import com.fastcampus.blog.common.error.ResourceNotFoundException
import com.fastcampus.blog.common.util.mapToCommentResponse
import com.fastcampus.blog.dto.request.CreateCommentRequest
import com.fastcampus.blog.dto.response.CommentResponse
import com.fastcampus.blog.model.Comment
import com.fastcampus.blog.model.UserInfo
import com.fastcampus.blog.repository.ArticleRepository
import com.fastcampus.blog.repository.CommentRepository
import com.fastcampus.blog.service.CommentService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
   private val articleRepository: ArticleRepository,
   private val commentRepository: CommentRepository,
   private val messageSource: MessageSourceUtil
) : CommentService {
   override fun findAll(slug: String): List<CommentResponse> {
      val article = articleRepository.findBySlugAndIsDeleted(slug, false) ?:
      throw ResourceNotFoundException(messageSource.getMessage(ErrorMessageKeys.NOT_FOUND, "Article"))
      return commentRepository.findAllByArticleIdAndIsDeleted(article.articleId!!, false).map {
         it.mapToCommentResponse()
      }
   }

   override fun findById(
      slug: String,
      commentId: Long,
   ): CommentResponse {
      val article = articleRepository.findBySlugAndIsDeleted(slug, false) ?: throw ResourceNotFoundException(
         messageSource.getMessage(ErrorMessageKeys.NOT_FOUND, "Article")
      )
      return commentRepository
         .findByCommentIdAndIsDeleted(commentId, false)?.mapToCommentResponse() ?: throw
      ResourceNotFoundException(messageSource.getMessage(ErrorMessageKeys.NOT_FOUND, "Comment"))

   }

   override fun create(
      slug: String,
      request: CreateCommentRequest,
   ): CommentResponse {
      val user = (SecurityContextHolder.getContext().authentication.principal as UserInfo).user
      val article = articleRepository.findBySlugAndIsDeleted(slug, false) ?: throw ResourceNotFoundException(
         messageSource.getMessage(ErrorMessageKeys.NOT_FOUND, "Article")
      )
      val comment = Comment(
         articleId = article.articleId,
         userId = user.userId,
         content = request.content
      )
      return commentRepository.save(comment).mapToCommentResponse()
   }

   override fun delete(slug: String, commentId: Long) {
      val article = articleRepository.findBySlugAndIsDeleted(slug, false) ?: throw ResourceNotFoundException(
         messageSource.getMessage(ErrorMessageKeys.NOT_FOUND, "Article")
      )
      val comment = commentRepository
         .findByCommentIdAndIsDeleted(commentId, false) ?: throw
      ResourceNotFoundException(messageSource.getMessage(ErrorMessageKeys.NOT_FOUND, "Comment"))
      comment.isDeleted = true
      commentRepository.save(comment)
   }
}