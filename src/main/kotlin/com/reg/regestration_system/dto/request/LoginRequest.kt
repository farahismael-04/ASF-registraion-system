
package com.reg.regestration_system.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(

    @field:NotBlank
    @field:Size(max = 50)
    val username: String,

    @field:NotBlank
    @field:Size(max = 128)
    val password: String
)
