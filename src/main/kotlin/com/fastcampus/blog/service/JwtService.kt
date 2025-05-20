package com.fastcampus.blog.service

import org.springframework.security.core.userdetails.UserDetails

interface JwtService {

   fun generateToken(userDetails: UserDetails): String
   fun generateToken(username: String): String
   fun validateToken(token: String): Boolean
   fun getUsername(token: String): String
}