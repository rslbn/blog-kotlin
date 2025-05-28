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
@Table(name = "categories", schema = "public")
class Category(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var categoryId: Int? = null,
   var name: String? = null,
   var description: String?,
   @Column(name = "is_deleted", nullable = false)
   var isDeleted: Boolean = false,
   @CreationTimestamp
   @Column(name = "created_at", nullable = false)
   var createdAt: LocalDateTime? = null,
   @Column(name = "updated_at")
   @UpdateTimestamp
   var updatedAt: LocalDateTime? = null,
)