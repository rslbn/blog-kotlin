package com.fastcampus.blog.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserInfo(
   val user: User,
   val roles: List<Role>
): UserDetails {

   override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
      return roles.map { SimpleGrantedAuthority(it.name) }.toMutableSet()
   }

   override fun getPassword(): String {
      return user.password
   }

   override fun getUsername(): String {
      return user.username
   }

   override fun isAccountNonExpired(): Boolean {
      return true
   }

   override fun isAccountNonLocked(): Boolean {
      return true
   }

   override fun isCredentialsNonExpired(): Boolean {
      return true
   }

   override fun isEnabled(): Boolean {
      return true
   }
}