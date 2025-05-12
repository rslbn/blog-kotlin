package com.fastcampus.blog.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "authors", schema = "public")
class Author (
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var authorId: Long,
   @Column(name = "user_id")
   var userId: Long,
   var bio: String?,
   @Column(unique = true)
   var alias: String?,
   @CreationTimestamp
   @Column(name = "created_at")
   var createdAt: LocalDateTime,
   @UpdateTimestamp
   @Column(name = "updated_at")
   var updatedAt: LocalDateTime,
   @Column(name = "is_deleted")
   var isDeleted: Boolean = false
)