package com.fastcampus.blog.repository

import com.fastcampus.blog.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

   fun findByUsernameAndIsDeleted(username: String, isDeleted: Boolean): User?
   fun existsByUsernameAndIsDeleted(username: String, isDeleted: Boolean): Boolean
   fun existsByEmail(email: String): Boolean
}