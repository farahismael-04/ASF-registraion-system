package com.reg.regestration_system.exception

data class ApiErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: String = java.time.Instant.now().toString()
)