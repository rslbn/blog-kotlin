package com.fastcampus.blog.service

import com.fastcampus.blog.dto.request.user.LoginRequest
import com.fastcampus.blog.dto.response.LoginResponse

interface AuthService {

   fun login(request: LoginRequest): LoginResponse
}