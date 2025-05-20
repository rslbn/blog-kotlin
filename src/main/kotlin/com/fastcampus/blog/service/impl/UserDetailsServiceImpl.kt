package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.error.ResourceNotFoundException
import com.fastcampus.blog.model.UserInfo
import com.fastcampus.blog.repository.RoleRepository
import com.fastcampus.blog.repository.UserRepository
import com.fastcampus.blog.repository.UserRoleRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
   private val userRepository: UserRepository,
   private val userRoleRepository: UserRoleRepository,
   private val roleRepository: RoleRepository
): UserDetailsService {
   override fun loadUserByUsername(username: String?): UserDetails {
      val user = userRepository.findByUsernameAndIsDeleted(username!!, false) ?: throw ResourceNotFoundException("User not found, $username")
      val roles = roleRepository.findRolesByUsername(username)
      val userInfo = UserInfo(user, roles)
      return userInfo
   }
}