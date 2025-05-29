package com.fastcampus.blog.service.impl

import com.fastcampus.blog.common.ErrorMessageKeys
import com.fastcampus.blog.common.ErrorMessageKeys.ERROR_ALREADY_EXIST
import com.fastcampus.blog.common.MessageSourceUtil
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
   private val messageSource: MessageSourceUtil,
   private val userRepository: UserRepository,
   private val passwordEncoder: PasswordEncoder,
   private val userRoleRepository: UserRoleRepository
) : UserService {

   @Transactional
   override fun register(request: RegisterRequest): UserResponse {
      var user: User? = null
      with(request) {
         if (userRepository.existsByUsernameAndIsDeleted(username!!, false)) 
            throw UsernameAlreadyExistsException(
               messageSource.getMessage(ERROR_ALREADY_EXIST, "User"))

         if (userRepository.existsByEmail(email!!)) throw EmailAlreadyExistsException(
            messageSource.getMessage(ERROR_ALREADY_EXIST, "Email")
         )

         if (password != null && confirmationPassword != null) {
            if (password != confirmationPassword) throw InvalidPasswordException(
               messageSource.getMessage(ErrorMessageKeys.PASSWORD_CONFIRMATION_NOT_MATCH)
            )
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
         UserRole(userRoleId = UserRole.UserRoleId(user.userId!!, 4))// roleId for USER role)
      )
      userRoleRepository.saveAll(roles)
      return user.mapToUserResponse()
   }

   override fun updateProfile(username: String, request: UpdateUserProfileRequest): UserResponse {
      val user: User? = userRepository.findByUsernameAndIsDeleted(username, false)
         ?: throw ResourceNotFoundException(
            messageSource.getMessage(ErrorMessageKeys.NOT_FOUND_CAUSE, arrayOf("Username", username))
         )
      with (request) {
         if (!request.username.isNullOrBlank()) {
            if (userRepository.existsByUsernameAndIsDeleted(request.username, false))
               throw UsernameAlreadyExistsException(
                  messageSource.getMessage(ERROR_ALREADY_EXIST, "Username")
               )
            if (!request.username.matches("^[\\w@_.]+$".toRegex()))
               throw BadRequestException(
                  messageSource.getMessage(ErrorMessageKeys.INVALID_USERNAME_PATTERN)
               )
            user?.username = request.username
         }
         if (!firstname.isNullOrBlank())
            if (validateName(firstname)) user?.firstname = firstname
            else throw BadRequestException(messageSource.getMessage(ErrorMessageKeys.INVALID_NAME, "Firstname"))
         if (!lastname.isNullOrBlank())
            if (validateName(lastname)) user?.lastname = lastname
            else throw BadRequestException(messageSource.getMessage(ErrorMessageKeys.INVALID_NAME, "Lastname"))
      }
      return userRepository.save(user!!).mapToUserResponse()
   }

   override fun changePassword(request: ChangePasswordRequest): UserResponse {
      var user: User? = null
      with(request) {
         user = userRepository.findByUsernameAndIsDeleted(username!!, false) ?:
            throw ResourceNotFoundException(
               messageSource.getMessage(
                  ErrorMessageKeys.NOT_FOUND_CAUSE, arrayOf("Username", request.username!!))
            )
         if (passwordEncoder.matches(newPassword, user.password))
            throw InvalidPasswordException(
               messageSource.getMessage(ErrorMessageKeys.PASSWORD_IS_SAME_AS_OLD_PASSWORD)
            )
         if (confirmationPassword.isNullOrBlank())
            throw InvalidPasswordException(messageSource.getMessage(ErrorMessageKeys.CONFIRMATION_PASSWORD_IS_BLANK))
         if (newPassword != confirmationPassword)
            throw InvalidPasswordException(messageSource.getMessage(ErrorMessageKeys.PASSWORD_CONFIRMATION_NOT_MATCH))

         val encodedNewPassword = passwordEncoder.encode(newPassword)
         user.password = encodedNewPassword
      }
      return userRepository.save(user!!).mapToUserResponse()
   }

   override fun changeEmail(request: ChangeEmailRequest): UserResponse {
      var user: User? = null
      with(request) {
         user = userRepository.findByUsernameAndIsDeleted(username!!, false)
            ?: throw ResourceNotFoundException(
               messageSource.getMessage(
                  ErrorMessageKeys.NOT_FOUND_CAUSE, arrayOf("Username", request.username!!))
            )
         if (email == user.email) throw BadRequestException(
            messageSource.getMessage(ErrorMessageKeys.EMAIL_SAME_AS_OLD_EMAIL)
         )
         if (userRepository.existsByEmail(email!!)) throw EmailAlreadyExistsException(
            messageSource.getMessage(ERROR_ALREADY_EXIST, "Email")
         )
         user.email = email
         if (!passwordEncoder.matches(confirmationPassword, user.password))
            throw InvalidPasswordException(messageSource.getMessage(ErrorMessageKeys.PASSWORD_CONFIRMATION_NOT_MATCH))
      }
      return userRepository.save(user!!).mapToUserResponse()
   }

   override fun delete(username: String) {
      val user = userRepository.findByUsernameAndIsDeleted(username, false) ?: throw ResourceNotFoundException(
         messageSource.getMessage(
            ErrorMessageKeys.NOT_FOUND_CAUSE, arrayOf("Username", username))
      )
      user.isDeleted = true
      val userRoles = userRoleRepository.deleteByUserId(user.userId!!)
   }

   private fun validateName(target: String) = target.matches("^[a-z A-Z]+$".toRegex())
}