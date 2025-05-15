package com.fastcampus.blog.controller

import com.fastcampus.blog.dto.request.user.LoginRequest
import com.fastcampus.blog.dto.response.ApiResponse
import com.fastcampus.blog.dto.response.LoginResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

   @PostMapping("/login")
   fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<ApiResponse<LoginResponse>> = ResponseEntity.ok(
      ApiResponse("Login success", HttpStatus.OK.name, HttpStatus.OK.value())
   )
}