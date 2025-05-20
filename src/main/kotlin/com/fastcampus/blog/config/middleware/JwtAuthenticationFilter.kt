package com.fastcampus.blog.config.middleware

import com.fastcampus.blog.service.JwtService
import com.fastcampus.blog.service.impl.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
   private val jwtService: JwtService,
   private val userDetailsService: UserDetailsServiceImpl
): OncePerRequestFilter() {

   override fun doFilterInternal(
      request: HttpServletRequest,
      response: HttpServletResponse,
      filterChain: FilterChain
   ) {
      // Skip authentication for paths that don't need it
      if (request.servletPath.contains("/auth/")) {
         filterChain.doFilter(request, response)
         return
      }

      val authHeader = request.getHeader("Authorization")
      val test = request.getHeader("Testing-something")
      logger.info("Header: $authHeader")
      logger.info("Testing-something: $test")

      // If no authorization header or not Bearer token, continue to next filter
      if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
         filterChain.doFilter(request, response)
         return
      }

      try {
         val jwtToken = authHeader.substring(7) // Skip "Bearer "
         val username = jwtService.getUsername(jwtToken)

         // Only attempt authentication if username exists and no authentication is set
         if (username.isNotBlank() && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)

            if (jwtService.validateToken(jwtToken)) {
               val authToken = UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.authorities
               )
               authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
               SecurityContextHolder.getContext().authentication = authToken
            }
         }
      } catch (e: Exception) {
         // Log exception but don't throw it
         logger.error("JWT Authentication failed: ${e.message}")
      }

      // Always continue filter chain
      filterChain.doFilter(request, response)
   }
}