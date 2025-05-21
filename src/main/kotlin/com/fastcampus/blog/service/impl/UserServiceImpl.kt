package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.error.BadRequestException
import com.fastcampus.blog.common.error.CredentialsException
import com.fastcampus.blog.common.error.EmailAlreadyExistsException
import com.fastcampus.blog.common.error.InvalidPasswordException
import com.fastcampus.blog.common.error.ResourceNotFoundException
import com.fastcampus.blog.common.error.UsernameAlreadyExistsException
import com.fastcampus.blog.common.util.mapToUserResponse
import com.fastcampus.blog.dto.request.user.ChangeEmailRequest
import com.fastcampus.blog.dto.request.user.ChangePasswordRequest
import com.fastcampus.blog.dto.request.user.RegisterRequest
import com.fastcampus.blog.dto.request.user.UpdateUserProfileRequest
import com.fastcampus.blog.dto.response.UserResponse
import com.fastcampus.blog.model.User
import com.fastcampus.blog.model.UserRole
import com.fastcampus.blog.repository.UserRepository
import com.fastcampus.blog.repository.UserRoleRepository
import com.fastcampus.blog.service.UserService
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
   private val userRepository: UserRepository,
   private val passwordEncoder: PasswordEncoder,
   private val userRoleRepository: UserRoleRepository,
) : UserService {

   @Transactional
   override fun register(request: RegisterRequest): UserResponse {
      var user: User? = null
      with(request) {
         if (userRepository.existsByUsernameAndIsDeleted(username!!, false)) throw UsernameAlreadyExistsException("Username already exist!")

         if (userRepository.existsByEmail(email!!)) throw EmailAlreadyExistsException("Email already exist!")

         if (password != null && confirmationPassword != null) {
            if (password != confirmationPassword) throw InvalidPasswordException("Password does not match the confirmation password!")
         }

         val encodedPassword = passwordEncoder.encode(password)
         user = User(
            username = username,
            password = encodedPassword,
            email = email,
            firstname = firstname!!,
            lastname = lastname!!
         )
      }
      user = userRepository.save(user!!)
      val roles: List<UserRole> = listOf(
         UserRole(userRoleId = UserRole.UserRoleId(user?.userId!!, 4))// roleId for USER role)
      )
      userRoleRepository.saveAll(roles)
      return user!!.mapToUserResponse()
   }

   override fun updateProfile(username: String, request: UpdateUserProfileRequest): UserResponse {
      val user: User? = userRepository.findByUsernameAndIsDeleted(username, false)
         ?: throw ResourceNotFoundException("User not found with username, ${username}!")
      with (request) {
         if (!request.username.isNullOrBlank()) {
            // TODO: Maybe change with new method, to check if username is exist or not
            if (userRepository.existsByUsernameAndIsDeleted(request.username, false)) throw UsernameAlreadyExistsException("Username already exist!")
            if (!request.username.matches("^[\\w@_.]+$".toRegex()))
               throw BadRequestException("Username can only contain this")
            user?.username = request.username
         }
         if (!firstname.isNullOrBlank())
            if (validateName(firstname)) user?.firstname = firstname
            else throw BadRequestException("firstname is invalid")
         if (!lastname.isNullOrBlank())
            if (validateName(lastname)) user?.lastname = lastname
            else throw BadRequestException("lastname is invalid")
      }
      return userRepository.save(user!!).mapToUserResponse()
   }

   override fun changePassword(request: ChangePasswordRequest): UserResponse {
      var user: User? = null
      with(request) {
         user = userRepository.findByUsernameAndIsDeleted(username!!, false) ?:
            throw ResourceNotFoundException("User not found with username, ${username}!")
         if (passwordEncoder.matches(newPassword, user?.password))
            throw InvalidPasswordException("Cannot use password you've been used before!")
         if (confirmationPassword.isNullOrBlank())
            throw InvalidPasswordException("Confirmation password is required!")
         if (newPassword != confirmationPassword)
            throw InvalidPasswordException("Password does not match!")

         val encodedNewPassword = passwordEncoder.encode(newPassword)
         user?.password = encodedNewPassword
      }
      return userRepository.save(user!!).mapToUserResponse()
   }

   override fun changeEmail(request: ChangeEmailRequest): UserResponse {
      var user: User? = null
      with(request) {
         user = userRepository.findByUsernameAndIsDeleted(username!!, false)
            ?: throw ResourceNotFoundException("User not found with username, ${username}!")
         if (email == user?.email) throw EmailAlreadyExistsException("cannot use the same email")
         if (userRepository.existsByEmail(email!!)) throw EmailAlreadyExistsException("Email already exist!")
         user?.email = email
         if (!passwordEncoder.matches(confirmationPassword, user?.password))
            throw CredentialsException("Password does not match!")
      }
      return userRepository.save(user!!).mapToUserResponse()
   }

   override fun delete(username: String) {
      val user = userRepository.findByUsernameAndIsDeleted(username, false) ?: throw ResourceNotFoundException("")
      user.isDeleted = true
      val userRoles = userRoleRepository.deleteByUserId(user.userId!!)
   }

   private fun validateName(target: String) = target.matches("^[a-z A-Z]+$".toRegex())
}