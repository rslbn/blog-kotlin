package com.fastcampus.blog.service

import com.fastcampus.blog.dto.request.user.ChangeEmailRequest
import com.fastcampus.blog.dto.request.user.ChangePasswordRequest
import com.fastcampus.blog.dto.request.user.RegisterRequest
import com.fastcampus.blog.dto.request.user.UpdateUserProfileRequest
import com.fastcampus.blog.dto.response.UserResponse

interface UserService {
   fun register(request: RegisterRequest): UserResponse
   fun updateProfile(username: String, request: UpdateUserProfileRequest): UserResponse
   fun changePassword(request: ChangePasswordRequest): UserResponse
   fun changeEmail(request: ChangeEmailRequest): UserResponse
   fun delete(username: String)
}