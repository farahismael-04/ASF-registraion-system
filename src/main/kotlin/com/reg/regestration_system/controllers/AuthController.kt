package com.reg.regestration_system.controllers

import com.reg.regestration_system.repository.AppUserRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

data class CurrentUserResponse(
    val userId: Long,
    val username: String
)

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val appUserRepository: AppUserRepository
) {

    @GetMapping("/me")
    fun getCurrentUser(
        @AuthenticationPrincipal jwt: Jwt
    ): CurrentUserResponse {

        val username = jwt.subject
            ?: throw IllegalArgumentException("Invalid token")

        val user = appUserRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        return CurrentUserResponse(
            userId = requireNotNull(user.userId),
            username = user.username
        )
    }
}