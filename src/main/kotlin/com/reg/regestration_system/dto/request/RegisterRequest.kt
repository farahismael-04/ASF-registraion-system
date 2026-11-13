package com.reg.regestration_system.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class SecurityAnswerRequest(

    @field:NotNull
    val questionId: Long,

    @field:NotBlank
    @field:Size(max = 255)
    val answer: String
)