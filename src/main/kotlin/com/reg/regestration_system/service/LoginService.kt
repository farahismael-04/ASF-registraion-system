package com.reg.regestration_system.service

import com.reg.regestration_system.dto.request.LoginRequest
import com.reg.regestration_system.dto.response.LoginResponse
import com.reg.regestration_system.repository.AppUserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class LoginService(
    private val appUserRepository: AppUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    @Transactional
    fun login(request: LoginRequest): LoginResponse {

        val user = appUserRepository.findByUsername(request.username.trim())
            ?: throw IllegalArgumentException("Invalid username or password")

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw IllegalArgumentException("Invalid username or password")
        }

        val userId = requireNotNull(user.userId)

        val token = jwtService.generateToken(
            userId = userId,
            username = user.username,
            tokenVersion = user.tokenVersion

        )

        user.lastLoginAt = OffsetDateTime.now()
        appUserRepository.save(user)

        return LoginResponse(
            userId = userId,
            username = user.username,
            accessToken = token,
            tokenType = "Bearer",
            expiresIn = jwtService.getExpirationSeconds(),
            message = "Login successful"
        )
    }
}