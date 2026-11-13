package com.reg.regestration_system.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(

    @field:NotBlank
    @field:Size(min = 3, max = 50)
    val username: String,

    @field:NotBlank
    @field:Size(min = 12, max = 128)
    val password: String,

    @field:Valid
    @field:Size(min = 3, max = 3)
    val securityAnswers: List<SecurityAnswerRequest>
)