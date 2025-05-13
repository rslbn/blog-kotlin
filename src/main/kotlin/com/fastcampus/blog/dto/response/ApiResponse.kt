package com.fastcampus.blog.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T> (
   val message: String? = null ,
   val status: String,
   val statusCode: Int,
   val data: T? = null,
   val meta: Metadata? = null
) {
   data class Metadata(
      val pageNumber: Int,
      val pageSize: Int
   )
}