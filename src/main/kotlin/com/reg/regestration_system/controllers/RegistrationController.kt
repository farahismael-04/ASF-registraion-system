
package com.reg.regestration_system.controllers

import com.reg.regestration_system.service.RegistrationService
import com.reg.regestration_system.dto.request.RegisterRequest
import com.reg.regestration_system.dto.response.RegisterResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class RegistrationController(
    private val registrationService: RegistrationService
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
        @Valid @RequestBody request: RegisterRequest
    ): RegisterResponse {

        return registrationService.register(request)
    }
}
