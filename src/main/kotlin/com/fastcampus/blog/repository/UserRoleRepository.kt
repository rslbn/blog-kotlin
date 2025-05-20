package com.fastcampus.blog.repository

import com.fastcampus.blog.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRoleRepository: JpaRepository<UserRole, UserRole.UserRoleId> {

   @Query(value = """
      SELECT * from users_roles where user_id = :userId
   """, nativeQuery = true)
   fun findByUserId(@Param("userId") userId: Long): List<UserRole>

   @Query(value = """
      DELETE FROM users_roles where user_id = :userId
   """, nativeQuery = true)
   fun deleteByUserId(@Param("userId") userId: Long)

}