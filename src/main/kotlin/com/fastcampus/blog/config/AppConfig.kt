package com.fastcampus.blog.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

@Configuration
class AppConfig {

   @Bean
   fun messageSource() = ReloadableResourceBundleMessageSource().apply {
      setBasenames("classpath:/messages/messages", "classpath:/messages/error_messages")
      setDefaultEncoding("UTF-8")
   }
}