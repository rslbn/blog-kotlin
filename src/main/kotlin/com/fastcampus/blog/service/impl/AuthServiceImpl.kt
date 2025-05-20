package com.fastcampus.blog.service.impl

import com.fastcampus.blog.dto.request.user.LoginRequest
import com.fastcampus.blog.dto.response.LoginResponse
import com.fastcampus.blog.model.UserInfo
import com.fastcampus.blog.service.AuthService
import com.fastcampus.blog.service.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
   private val authenticationManager: AuthenticationManager,
   private val jwtService: JwtService
) : AuthService {
   override fun login(request: LoginRequest): LoginResponse {
      return try {
         val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
               request.username, request.password
            )
         )
         val user = (authentication.principal as UserInfo)
         LoginResponse(
            token = "Bearer " + jwtService.generateToken(user.username),
            username = user.username,
            type = "Bearer"
         )
      } catch (exception: Exception) {
         throw RuntimeException(exception.message)
      }
   }
}