package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.util.toDate
import com.fastcampus.blog.service.JwtService
import io.jsonwebtoken.Jwts
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.crypto.SecretKey
import kotlin.math.sign

@Service
class JwtServiceImpl(
   private val signKey: SecretKey
): JwtService {

   override fun generateToken(userDetails: UserDetails): String {
      return ""
   }

   override fun generateToken(username: String): String {
      val expirationTime = LocalDateTime.now().plusDays(30)
      val expiration = expirationTime.toDate()
      return Jwts.builder()
         .subject(username)
         .issuedAt(Date())
         .expiration(expiration)
         .signWith(signKey)
         .compact()
   }

   override fun validateToken(token: String): Boolean {
     return try {
        val parser = Jwts.parser().verifyWith(signKey).build()
        parser.parseSignedClaims(token)
        return true
     } catch (exception: Exception) {
        return false
     }
   }

   override fun getUsername(token: String): String {
     val parser = Jwts.parser().verifyWith(signKey).build()
      return parser.parseSignedClaims(token)
         .payload
         .subject
   }
}