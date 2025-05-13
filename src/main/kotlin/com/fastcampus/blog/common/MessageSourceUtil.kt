package com.fastcampus.blog.common

import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class MessageSourceUtil (private val messageSource: MessageSource) {

   fun getMessage(code: String, args: Array<Any>, locale: Locale) =
      messageSource.getMessage(code, args, locale)

   fun getMessage(code: String, arg: String) = getMessage(code, arrayOf(arg), Locale.getDefault())

   fun getMessage(code: String, args: Array<Any>) = getMessage(code, args, Locale.getDefault())

   fun getMessage(code: String) = getMessage(code, emptyArray())
}