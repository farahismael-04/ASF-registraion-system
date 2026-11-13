
package com.reg.regestration_system.dto.response

data class LoginResponse(
    val userId: Long,
    val username: String,
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val message: String
)