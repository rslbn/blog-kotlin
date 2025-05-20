package com.fastcampus.blog.config

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey

@Configuration
class JwtConfig (
   @Value("\${jwt.secret}")
   private val jwtSecret: String
){
   @Bean
   fun signKey(): SecretKey{
      return Keys.hmacShaKeyFor(jwtSecret.toByteArray(StandardCharsets.UTF_8))
   }
}