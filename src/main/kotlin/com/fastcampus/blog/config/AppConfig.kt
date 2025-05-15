package com.fastcampus.blog.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@Configuration
class AppConfig {

   @Bean
   fun messageSource(): MessageSource = ReloadableResourceBundleMessageSource().apply {
      setBasenames("classpath:/messages/messages", "classpath:/messages/error_messages")
      setDefaultEncoding("UTF-8")
   }
}