package com.fastcampus.blog.common.error

class ResourceNotFoundException(override val message: String): RuntimeException(message)