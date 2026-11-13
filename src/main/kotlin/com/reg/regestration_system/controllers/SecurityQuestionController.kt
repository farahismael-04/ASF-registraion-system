package com.reg.regestration_system.controllers

import com.reg.regestration_system.dto.response.SecurityQuestionResponse
import com.reg.regestration_system.repository.SecurityQuestionRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/security-questions")
class SecurityQuestionController(
    private val securityQuestionRepository: SecurityQuestionRepository
) {

    @GetMapping
    fun getAllQuestions(): List<SecurityQuestionResponse> {

        return securityQuestionRepository.findAll().map { question ->
            SecurityQuestionResponse(
                questionId = requireNotNull(question.questionId),
                questionText = question.questionText
            )
        }
    }
}