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
@Table(name = "users", schema = "public")
class User (
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var userId: Long? = null,

   @Column(nullable = false, unique = true)
   var username: String,

   @Column(nullable = false)
   var password: String,

   @Column(nullable = false, unique = true)
   var email: String?,

   var firstname: String,
   var lastname: String,

   @CreationTimestamp
   @Column(name = "created_at")
   var createdAt: LocalDateTime? = null,

   @UpdateTimestamp
   @Column(name = "updated_at")
   var updatedAt: LocalDateTime? = null,

   @Column(name = "is_deleted")
   var isDeleted: Boolean = false
) {
   fun getFullname(): String = "$firstname $lastname"

   override fun toString(): String = "User(userId=$userId, username=$username, fullname=$firstname + $lastname}"

   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as User

      if (userId != other.userId) return false
      if (username != other.username) return false
      if (email != other.email) return false

      return true
   }

   override fun hashCode(): Int {
      var result = userId.hashCode()
      result = 31 * result + username.hashCode()
      result = 31 * result + (email?.hashCode() ?: 0)
      return result
   }
}