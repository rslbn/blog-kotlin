package com.fastcampus.blog.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users_roles", schema = "public")
class UserRole(
   @EmbeddedId
   var userRoleId: UserRoleId,

   @Column
   @CreationTimestamp
   var createdAt: LocalDateTime? = null,

   @Column
   @UpdateTimestamp
   var updatedAt: LocalDateTime? = null
) {
   @Embeddable
   data class UserRoleId(
      @Column val userId: Long, @Column val roleId: Int
   )
}