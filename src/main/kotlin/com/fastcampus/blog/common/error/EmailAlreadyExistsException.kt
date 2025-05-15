package com.fastcampus.blog.common.error

class EmailAlreadyExistsException (override val message: String): RuntimeException(message) {
}