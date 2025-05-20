package com.fastcampus.blog.controller

import com.fastcampus.blog.common.util.createApiResponse
import com.fastcampus.blog.dto.request.user.LoginRequest
import com.fastcampus.blog.dto.response.ApiResponse
import com.fastcampus.blog.dto.response.LoginResponse
import com.fastcampus.blog.service.AuthService
import com.fastcampus.blog.service.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
   private val jwtService: JwtService,
   private val authService: AuthService
) {
   @GetMapping("/jwt")
   fun testJwt(@RequestParam username: String): String {
      return jwtService.generateToken(username)
   }

   @GetMapping("/jwt/username")
   fun testJwtGetUsername(@RequestParam token: String) = jwtService.getUsername(token)

   @PostMapping("/login")
   fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<ApiResponse<LoginResponse>> = ResponseEntity.ok(
     createApiResponse("Login success", HttpStatus.OK, authService.login(loginRequest))
   )
}