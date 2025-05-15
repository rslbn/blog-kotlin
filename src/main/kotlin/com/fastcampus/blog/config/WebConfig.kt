package com.fastcampus.blog.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class WebConfig {

   @Bean
   fun corsConfigurationSource(): CorsConfigurationSource =
      UrlBasedCorsConfigurationSource().apply {
         val corsConfiguration: CorsConfiguration = CorsConfiguration().apply {
            allowedHeaders = listOf("Authorization")
            allowedOrigins = listOf("*")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
         }
         registerCorsConfiguration("/**", corsConfiguration)
      }
}