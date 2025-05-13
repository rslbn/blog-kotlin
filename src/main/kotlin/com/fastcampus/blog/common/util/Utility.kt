package com.fastcampus.blog.common.util

import com.fastcampus.blog.dto.response.ApiResponse
import org.springframework.http.HttpStatus

fun <T> createApiResponse(
   message: String? = null, status: HttpStatus,
   data: T? = null, metadata: ApiResponse.Metadata? = null) =
   ApiResponse(message, status.name, status.value(), data, metadata)