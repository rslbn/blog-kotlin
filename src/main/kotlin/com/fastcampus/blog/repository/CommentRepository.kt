package com.fastcampus.blog.repository

import com.fastcampus.blog.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {

   fun findAllByUserId(userId: Long): List<Comment>
   fun findAllByArticleId(articleId: Long): List<Comment>
}