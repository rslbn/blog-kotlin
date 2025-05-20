package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.error.ResourceNotFoundException
import com.fastcampus.blog.model.UserInfo
import com.fastcampus.blog.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
   private val userRepository: UserRepository
): UserDetailsService {
   override fun loadUserByUsername(username: String?): UserDetails {
      val user = userRepository.findByUsernameAndIsDeleted(username!!, false) ?: throw ResourceNotFoundException("User not found, $username")
      val userInfo = UserInfo(user)
      return userInfo
   }
}