package com.reg.regestration_system.controllers

import com.reg.regestration_system.dto.request.PasswordResetRequest
import com.reg.regestration_system.dto.response.SecurityQuestionResponse
import com.reg.regestration_system.service.PasswordRecoveryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

data class PasswordResetResponse(
    val message: String
)

@RestController
@RequestMapping("/api/auth")
class PasswordRecoveryController(
    private val passwordRecoveryService: PasswordRecoveryService
) {

    @GetMapping("/recovery-questions/{username}")
    fun getRecoveryQuestions(
        @PathVariable username: String
    ): List<SecurityQuestionResponse> {
        return passwordRecoveryService.getRecoveryQuestions(username)
    }

    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    fun resetPassword(
        @Valid @RequestBody request: PasswordResetRequest
    ): PasswordResetResponse {

        val message = passwordRecoveryService.resetPassword(request)

        return PasswordResetResponse(message)
    }
}