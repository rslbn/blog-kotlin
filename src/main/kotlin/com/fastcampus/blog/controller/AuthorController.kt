package com.fastcampus.blog.controller

import com.fastcampus.blog.common.util.createApiResponse
import com.fastcampus.blog.dto.request.author.RegisterAuthorRequest
import com.fastcampus.blog.dto.response.ApiResponse
import com.fastcampus.blog.dto.response.AuthorResponse
import com.fastcampus.blog.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
class AuthorController(
   private val authorService: AuthorService
) {

   @GetMapping
   fun findAll(): ResponseEntity<ApiResponse<List<AuthorResponse>>> {
      return ResponseEntity.ok(createApiResponse(status = HttpStatus.OK, data = authorService.findAll()))
   }

   @GetMapping("/{authorId}")
   fun findById(@PathVariable authorId: Long): ResponseEntity<ApiResponse<AuthorResponse>> {
      return ResponseEntity.ok(createApiResponse(status = HttpStatus.OK, data = authorService.findById(authorId)))
   }

   @PostMapping("/register")
   fun register(@RequestBody request: RegisterAuthorRequest): ResponseEntity<ApiResponse<AuthorResponse>> =
      ResponseEntity.status(HttpStatus.CREATED).body(
         createApiResponse("Author is created!", HttpStatus.CREATED, authorService.register(request))
      )
}
