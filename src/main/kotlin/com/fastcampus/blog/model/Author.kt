package com.fastcampus.blog.model

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
@Table(name = "authors", schema = "public")
class Author (
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var authorId: Long? = null,
   @Column(name = "user_id")
   var userId: Long? = null,
   var bio: String?,
   @Column(unique = true)
   var alias: String?,
   @CreationTimestamp
   @Column(name = "created_at")
   var createdAt: LocalDateTime? = null,
   @UpdateTimestamp
   @Column(name = "updated_at")
   var updatedAt: LocalDateTime? = null,
   @Column(name = "is_deleted")
   var isDeleted: Boolean = false
)