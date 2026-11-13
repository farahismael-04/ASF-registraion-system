package com.reg.regestration_system.exception

class UsernameAlreadyExistsException(
    message: String = "Username already exists"
) : RuntimeException(message)