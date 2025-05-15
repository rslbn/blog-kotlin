package com.fastcampus.blog.common.error

class InvalidPasswordException(override val message: String): RuntimeException(message) {
}