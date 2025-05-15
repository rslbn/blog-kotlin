package com.fastcampus.blog.config.middleware

import com.fastcampus.blog.common.error.BadRequestException
import com.fastcampus.blog.common.error.ResourceNotFoundException
import com.fastcampus.blog.dto.response.ErrorResponse
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GenericExceptionHandler {

   @ExceptionHandler(BadRequestException::class, DataIntegrityViolationException::class)
   fun badRequestExceptionHandler(exception: Exception): ResponseEntity<ErrorResponse> {
      return ResponseEntity.badRequest().body(ErrorResponse(
         exception.message, HttpStatus.BAD_REQUEST
      ))
   }

   @ExceptionHandler(ResourceNotFoundException::class)
   fun resourceNotFoundExceptionHandler(exception: ResourceNotFoundException): ResponseEntity<ErrorResponse> =
     ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse(exception.message, HttpStatus.NOT_FOUND))

   @ExceptionHandler(Exception::class)
   fun globalExceptionHandler(exception: Exception) =
      ResponseEntity.internalServerError().body(ErrorResponse(exception.message, HttpStatus.INTERNAL_SERVER_ERROR))

   @ExceptionHandler(MethodArgumentNotValidException::class)
   fun methodArgumentNotValidExceptionHandler(
      exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
      val errorsMap: MutableMap<String, String> = HashMap()
      exception.fieldErrors.forEach { fieldError ->
         if (fieldError.defaultMessage != null) errorsMap[fieldError.field] = fieldError.defaultMessage!!
      }
      return ResponseEntity.badRequest()
         .body(ErrorResponse(httpStatus = HttpStatus.BAD_REQUEST, errors = errorsMap))
   }
}