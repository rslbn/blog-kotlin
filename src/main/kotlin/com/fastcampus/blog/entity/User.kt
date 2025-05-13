package com.fastcampus.blog.entity

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
@Table(name = "users", schema = "public")
data class User (
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var userId: Long,
   @Column(nullable = false, unique = true)
   var username: String,
   @Column(nullable = false)
   var password: String,
   @Column(nullable = false, unique = true)
   var email: String?,
   @CreationTimestamp
   @Column(name = "created_at")
   var createdAt: LocalDateTime,
   @UpdateTimestamp
   @Column(name = "updated_at")
   var updatedAt: LocalDateTime,
   @Column(name = "is_deleted")
   var isDeleted: Boolean = false
)