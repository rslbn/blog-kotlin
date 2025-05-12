package com.fastcampus.blog.repository

import com.fastcampus.blog.entity.Article
import com.fastcampus.blog.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

}