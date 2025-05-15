package com.fastcampus.blog.controller

import com.fastcampus.blog.common.util.createApiResponse
import com.fastcampus.blog.dto.request.user.ChangeEmailRequest
import com.fastcampus.blog.dto.request.user.ChangePasswordRequest
import com.fastcampus.blog.dto.request.user.RegisterRequest
import com.fastcampus.blog.dto.request.user.UpdateUserProfileRequest
import com.fastcampus.blog.dto.response.ApiResponse
import com.fastcampus.blog.dto.response.UserResponse
import com.fastcampus.blog.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/users")
class UserController(
   private val userService: UserService
) {
   @PostMapping("/register")
   fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<ApiResponse<UserResponse>> =
      ResponseEntity.status(HttpStatus.CREATED)
         .body(createApiResponse("User created", HttpStatus.CREATED, userService.register(request)))

   @PutMapping("/profile/{username}")
   fun updateProfile(@PathVariable username: String, @Valid @RequestBody request: UpdateUserProfileRequest):
           ResponseEntity<ApiResponse<UserResponse>> =
      ResponseEntity.ok(createApiResponse("User profile updated!", HttpStatus.OK,
         userService.updateProfile(username, request)))

   @PutMapping("/profile/{username}/change-password")
   fun changePassword(@PathVariable username: String, @Valid @RequestBody request:ChangePasswordRequest) =
      ResponseEntity.ok(
         createApiResponse("Password successfully changed!", HttpStatus.OK,
            userService.changePassword(request.also { it.username = username }))
      )

   @PutMapping("/profile/{username}/change-email")
   fun changeEmail(@PathVariable username: String, @Valid @RequestBody request:ChangeEmailRequest) =
      ResponseEntity.ok(
         createApiResponse("Email successfully changed!", HttpStatus.OK,
            userService.changeEmail(request.also { it.username = username }))
      )
}
