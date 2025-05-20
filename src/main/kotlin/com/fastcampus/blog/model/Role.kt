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
@Table(name = "roles", schema = "public")
class Role(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var roleId: Int,

   @Column
   var name: String,

   @Column
   var description: String,

   @Column
   var isDeleted: Boolean = false,

   @Column
   @CreationTimestamp
   var createdAt: LocalDateTime,

   @Column
   @UpdateTimestamp
   var updatedAt: LocalDateTime
) {
}