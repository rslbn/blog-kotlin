package com.fastcampus.blog.common.error

class ForbiddenUserAccessException(override val message: String): RuntimeException(message)