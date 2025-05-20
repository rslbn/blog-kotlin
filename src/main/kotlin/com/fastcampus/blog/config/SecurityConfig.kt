package com.fastcampus.blog.config

import com.fastcampus.blog.config.middleware.JwtAuthenticationFilter
import com.fastcampus.blog.service.impl.UserDetailsServiceImpl
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
   private val corsConfigurationSource: CorsConfigurationSource,
   private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

   @Bean
   fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
      return configuration.authenticationManager
   }

   @Bean
   fun authenticationProvider(userDetailsService: UserDetailsServiceImpl): AuthenticationProvider {
      return DaoAuthenticationProvider().apply {
         setPasswordEncoder(passwordEncoder())
         setUserDetailsService(userDetailsService)
      }
   }

   @Bean
   fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

   @Bean
   fun securityFilterChain(http: HttpSecurity,
                           authenticationProvider: AuthenticationProvider): SecurityFilterChain {

      return http
         .csrf { it.disable() }
         .cors { configurer -> configurer.configurationSource(corsConfigurationSource) }
         .authorizeHttpRequests { auth ->
            auth.requestMatchers("/auth/**").permitAll()
            auth.anyRequest().authenticated()
         }
         .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
         .authenticationProvider(authenticationProvider)
         .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
         .exceptionHandling { configurer ->
            configurer
               .authenticationEntryPoint { _, response, authException ->
                  response.status = HttpServletResponse.SC_UNAUTHORIZED
                  response.contentType = "application/json"
                  response.writer.write("""{"error":"Unauthorized","message":"${authException.message}"}""")
               }
               .accessDeniedHandler { _, response, accessDeniedException ->
                  response.status = HttpServletResponse.SC_FORBIDDEN
                  response.contentType = "application/json"
                  response.writer.write("""{"error":"Forbidden","message":"${accessDeniedException.message}"}""")
               }
         }
         .build()
   }

}