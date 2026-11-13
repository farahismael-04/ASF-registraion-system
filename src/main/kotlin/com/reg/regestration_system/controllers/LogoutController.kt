package com.reg.regestration_system.controllers

import com.reg.regestration_system.dto.response.LogoutResponse
import com.reg.regestration_system.repository.AppUserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class LogoutController(
    private val appUserRepository: AppUserRepository
) {

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    fun logout(
        @AuthenticationPrincipal jwt: Jwt
    ): LogoutResponse {

        val username = jwt.subject
            ?: throw IllegalArgumentException("Invalid token")

        val user = appUserRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        user.tokenVersion += 1
        appUserRepository.save(user)

        return LogoutResponse(
            message = "Logout successful"
        )
    }
}