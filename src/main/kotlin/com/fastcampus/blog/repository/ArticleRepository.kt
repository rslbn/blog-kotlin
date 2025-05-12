package com.fastcampus.blog.repository

import com.fastcampus.blog.entity.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository: JpaRepository<Article, Long> {

}