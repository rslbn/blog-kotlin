package com.fastcampus.blog.repository

import com.fastcampus.blog.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RoleRepository: JpaRepository<Role, Int> {

   fun findByName(name: String): Role?

   @Query(value = """
      SELECT r.* FROM ROLES r
      JOIN users_roles ur on r.role_id = ur.role_id
      JOIN users u on u.user_id = ur.user_id
      WHERE users.username = :username
   """, nativeQuery = true)
   fun findRolesByUsername(@Param("username") username: String): List<Role>
}