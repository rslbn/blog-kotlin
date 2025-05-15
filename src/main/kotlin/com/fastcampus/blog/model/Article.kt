package com.fastcampus.blog.model

import com.fastcampus.blog.common.util.toSlug
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "articles", schema = "public")
class Article (
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var articleId: Long? = null,
   @Column(name = "author_id")
   var authorId: Long? = null,
   @Column(nullable = false)
   var title: String,
   @Column(unique = true, nullable = false)
   var slug: String = title.toSlug(),
   var content: String? = null,
   @Column(name = "published_at")
   var publishedAt: LocalDateTime? = null,
   @CreationTimestamp
   @Column(name = "created_at")
   var createdAt: LocalDateTime ?= null,
   @UpdateTimestamp
   @Column(name = "updated_at")
   var updatedAt: LocalDateTime? = null,
   @Column(name = "is_deleted")
   var isDeleted: Boolean = false,
   @Column(name = "is_published")
   var isPublished: Boolean = false
)