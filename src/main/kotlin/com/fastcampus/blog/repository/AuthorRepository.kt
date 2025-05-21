package com.fastcampus.blog.repository

import com.fastcampus.blog.model.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository: JpaRepository<Author, Long> {
   fun findByUserId(userId: Long): Author?
   fun existsByUserId(userId: Long): Boolean
}