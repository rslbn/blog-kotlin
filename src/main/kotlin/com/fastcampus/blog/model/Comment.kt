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
@Table(name = "comments", schema = "public")
class Comment(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   var commentId: Long? = null,
   @Column(name = "article_id", nullable = false)
   var articleId: Long? = null,
   @Column(name = "user_id", nullable = false)
   var userId: Long? = null,
   var content: String? = null,
   @CreationTimestamp
   @Column(name = "created_at", nullable = false)
   var createdAt: LocalDateTime? = null,
   @Column(name = "updated_at")
   @UpdateTimestamp
   var updatedAt: LocalDateTime? = null,
   @Column("is_deleted")
   var isDeleted: Boolean = false
) {

}