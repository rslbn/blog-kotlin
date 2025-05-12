package com.fastcampus.blog.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "articles", schema = "public")
class Article (
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var articleId: Long,
   @Column(name = "author_id")
   var authorId: Long,
   @Column(nullable = false)
   var title: String,
   @Column(unique = true, nullable = false)
   var slug: String,
   var description: String?,
   var content: String?,
   @Column(name = "published_at")
   var publishedAt: LocalDateTime?,
   @CreationTimestamp
   @Column(name = "created_at")
   var createdAt: LocalDateTime,
   @UpdateTimestamp
   @Column(name = "updated_at")
   var updatedAt: LocalDateTime,
   @Column(name = "is_deleted")
   var isDeleted: Boolean = false
)