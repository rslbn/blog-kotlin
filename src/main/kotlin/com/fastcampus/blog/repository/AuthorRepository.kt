package com.fastcampus.blog.repository

import com.fastcampus.blog.entity.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository: JpaRepository<Author, Long> {

}