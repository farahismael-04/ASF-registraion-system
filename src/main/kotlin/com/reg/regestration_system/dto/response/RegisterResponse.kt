package com.reg.regestration_system.dto.response

data class RegisterResponse(
    val userId: Long,
    val username: String,
    val message: String
)