package com.fastcampus.blog.common.error

class UsernameAlreadyExistsException(override val message: String): RuntimeException(message)