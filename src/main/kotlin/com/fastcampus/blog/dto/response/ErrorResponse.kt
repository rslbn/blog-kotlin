package com.fastcampus.blog.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ErrorResponse(
   val message: String? = null,
   val httpStatus: HttpStatus,
   val errors: Map<String, String>? = null,
   val timestamp: LocalDateTime = LocalDateTime.now()
)