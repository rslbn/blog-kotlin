package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.error.ResourceNotFoundException
import com.fastcampus.blog.common.util.mapToAuthorResponse
import com.fastcampus.blog.dto.request.author.RegisterAuthorRequest
import com.fastcampus.blog.dto.response.AuthorResponse
import com.fastcampus.blog.model.Author
import com.fastcampus.blog.model.UserInfo
import com.fastcampus.blog.model.UserRole
import com.fastcampus.blog.repository.AuthorRepository
import com.fastcampus.blog.repository.RoleRepository
import com.fastcampus.blog.repository.UserRoleRepository
import com.fastcampus.blog.service.AuthorService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(
   private val authorRepository: AuthorRepository,
   private val userRoleRepository: UserRoleRepository,
   private val roleRepository: RoleRepository
) : AuthorService {
   override fun findAll(): List<AuthorResponse> {
      return authorRepository.findAll().map { it.mapToAuthorResponse() }
   }

   override fun findById(authorId: Long): AuthorResponse {
      return authorRepository
         .findById(authorId)
         .orElseThrow { ResourceNotFoundException("Author not found with id, ${authorId}!") }
         .mapToAuthorResponse()
   }

   override fun findByUserId(userId: Long): AuthorResponse {
      val author = authorRepository.findByUserId(userId)
         ?: throw ResourceNotFoundException("Author not found with userId, ${userId}!")
      return author.mapToAuthorResponse()
   }

   override fun register(request: RegisterAuthorRequest): AuthorResponse {
      val user = (SecurityContextHolder.getContext().authentication.principal as UserInfo).user
      if (  authorRepository.existsByUserId(user.userId!!)) throw RuntimeException("User already an author!")
      val author = Author(userId =  user.userId, bio = request.bio, alias = request.alias)
      val authorRole = roleRepository.findByName("AUTHOR") ?: throw ResourceNotFoundException("Role not found")
      val userRoleAuthor = UserRole(UserRole.UserRoleId(user.userId!!, authorRole.roleId))
      userRoleRepository.save(userRoleAuthor)
      return authorRepository.save(author).mapToAuthorResponse()
   }
}