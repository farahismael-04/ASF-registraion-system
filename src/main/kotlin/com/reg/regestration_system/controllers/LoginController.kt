package com.reg.regestration_system.controllers

import com.reg.regestration_system.dto.request.LoginRequest
import com.reg.regestration_system.dto.response.LoginResponse
import com.reg.regestration_system.service.LoginService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class LoginController(
    private val loginService: LoginService
) {

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): LoginResponse {
        return loginService.login(request)
    }
}