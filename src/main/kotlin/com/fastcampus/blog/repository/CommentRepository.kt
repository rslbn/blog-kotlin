package com.fastcampus.blog.repository

import com.fastcampus.blog.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {

   fun findByCommentIdAndIsDeleted(commentId: Long, isDeleted: Boolean): Comment?
   fun findAllByUserId(userId: Long): List<Comment>
   fun findAllByArticleIdAndIsDeleted(articleId: Long, isDelete: Boolean): List<Comment>
}