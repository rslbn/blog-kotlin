package com.fastcampus.blog.common.error

class BadRequestException(override val message: String): RuntimeException(message)