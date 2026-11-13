package com.reg.regestration_system.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleDuplicateUsername(
        ex: UsernameAlreadyExistsException
    ): ResponseEntity<ApiErrorResponse> {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiErrorResponse(
                status = 409,
                message = ex.message ?: "Username already exists"
            )
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(
        ex: IllegalArgumentException
    ): ResponseEntity<ApiErrorResponse> {

        return ResponseEntity.badRequest().body(
            ApiErrorResponse(
                status = 400,
                message = ex.message ?: "Invalid request"
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ApiErrorResponse> {

        val message = ex.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }

        return ResponseEntity.badRequest().body(
            ApiErrorResponse(
                status = 400,
                message = message.ifBlank { "Invalid request data" }
            )
        )
    }
}