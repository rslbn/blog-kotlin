package com.fastcampus.blog.controller

import com.fastcampus.blog.common.util.createApiResponse
import com.fastcampus.blog.dto.request.CreateArticleRequest
import com.fastcampus.blog.dto.request.UpdateArticleRequest
import com.fastcampus.blog.dto.response.ApiResponse
import com.fastcampus.blog.dto.response.ArticleDTO
import com.fastcampus.blog.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles")
class ArticleController(
   private val articleService: ArticleService
) {

   @GetMapping
   fun findAll(): ResponseEntity<ApiResponse<List<ArticleDTO>>> =
      ResponseEntity.ok(
         createApiResponse(status = HttpStatus.OK, data = articleService.findAll())
      )

   @GetMapping("/{slug}")
   fun findBySlug(@PathVariable slug: String): ResponseEntity<ApiResponse<ArticleDTO>> = ResponseEntity.ok(
      createApiResponse(
         status = HttpStatus.OK,
         data = articleService.findBySlug(slug)
      )
   )

   @PostMapping
   fun create(@RequestBody request: CreateArticleRequest): ResponseEntity<ApiResponse<ArticleDTO>> =
      ResponseEntity.status(HttpStatus.CREATED).body(
         createApiResponse("Article successfully created!",
            HttpStatus.CREATED,
            articleService.create(request)
         )
      )

   @PostMapping("/{slug}")
   fun publish(@PathVariable slug: String): ResponseEntity<ApiResponse<ArticleDTO>> = ResponseEntity.ok(
      createApiResponse("Article successfully published!", HttpStatus.OK,
         data = articleService.publish(slug)
      )
   )

   @PutMapping("/{slug}")
   fun update(@PathVariable slug: String,
              @RequestBody request: UpdateArticleRequest): ResponseEntity<ApiResponse<ArticleDTO>> =
      ResponseEntity.ok(createApiResponse("Article is updated!", HttpStatus.OK,
         articleService.update(slug, request)))

   @DeleteMapping("/{slug}")
   fun delete(@PathVariable slug: String): ResponseEntity<ApiResponse<Unit>> {
      articleService.delete(slug)
      return ResponseEntity.ok(createApiResponse("Article successfully deleted!", HttpStatus.OK))
   }
}
