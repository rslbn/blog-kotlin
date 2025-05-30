package com.fastcampus.blog.controller

import com.fastcampus.blog.common.util.createApiResponse
import com.fastcampus.blog.dto.request.CreateCommentRequest
import com.fastcampus.blog.dto.response.ApiResponse
import com.fastcampus.blog.dto.response.CommentResponse
import com.fastcampus.blog.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles/{slug}/comments")
class CommentController(
   private val commentService: CommentService
) {

   @GetMapping
   fun findAll(@PathVariable slug: String): ResponseEntity<ApiResponse<List<CommentResponse>>> {
      return ResponseEntity.ok(
         createApiResponse(status = HttpStatus.OK, data = commentService.findAll(slug))
      )
   }

   @GetMapping("/{commentId}")
   fun findById(@PathVariable slug: String, @PathVariable commentId: Long): ResponseEntity<ApiResponse<CommentResponse>> {
      return ResponseEntity.ok(
         createApiResponse(status = HttpStatus.OK, data = commentService.findById(slug, commentId))
      )
   }

   @PostMapping
   fun create(
      @PathVariable slug: String,
      @RequestBody request: CreateCommentRequest
   ): ResponseEntity<ApiResponse<CommentResponse>> {
      return ResponseEntity.status(HttpStatus.CREATED)
         .body(createApiResponse(
            "Comment is created!",
            HttpStatus.CREATED,
            commentService.create(slug, request)))
   }

   @DeleteMapping("/{commentId}")
   fun delete(@PathVariable slug: String, @PathVariable commentId: Long): ResponseEntity<ApiResponse<Void>> {
      commentService.delete(slug, commentId)
      return ResponseEntity.ok(
         createApiResponse(
            "Comment is deleted!",
            HttpStatus.OK
      ))
   }
}