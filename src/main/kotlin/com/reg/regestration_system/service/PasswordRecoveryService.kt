package com.reg.regestration_system.service

import com.reg.regestration_system.dto.request.PasswordResetRequest
import com.reg.regestration_system.dto.response.SecurityQuestionResponse
import com.reg.regestration_system.repository.AppUserRepository
import com.reg.regestration_system.repository.UserSecurityAnswerRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PasswordRecoveryService(
    private val appUserRepository: AppUserRepository,
    private val userSecurityAnswerRepository: UserSecurityAnswerRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getRecoveryQuestions(username: String): List<SecurityQuestionResponse> {

        val user = appUserRepository.findByUsername(username.trim())
            ?: throw IllegalArgumentException("Invalid username")

        val userId = requireNotNull(user.userId)

        return userSecurityAnswerRepository
            .findAllByUserUserId(userId)
            .map { answer ->
                SecurityQuestionResponse(
                    questionId = requireNotNull(answer.question.questionId),
                    questionText = answer.question.questionText
                )
            }
    }

    @Transactional
    fun resetPassword(request: PasswordResetRequest): String {

        val user = appUserRepository.findByUsername(request.username.trim())
            ?: throw IllegalArgumentException("Invalid username or security answers")

        val userId = requireNotNull(user.userId)

        val savedAnswers =
            userSecurityAnswerRepository.findAllByUserUserId(userId)

        if (savedAnswers.size != 3 ||
            request.securityAnswers.size != 3 ||
            request.securityAnswers.map { it.questionId }.distinct().size != 3
        ) {
            throw IllegalArgumentException("Invalid username or security answers")
        }

        val submittedAnswers = request.securityAnswers.associateBy {
            it.questionId
        }

        val allAnswersCorrect = savedAnswers.all { savedAnswer ->

            val questionId = requireNotNull(savedAnswer.question.questionId)

            val submittedAnswer = submittedAnswers[questionId]
                ?: return@all false

            passwordEncoder.matches(
                submittedAnswer.answer.trim().lowercase(),
                savedAnswer.answerHash
            )
        }

        if (!allAnswersCorrect) {
            throw IllegalArgumentException("Invalid username or security answers")
        }

        user.passwordHash = requireNotNull(
            passwordEncoder.encode(request.newPassword)
        )

        // Invalidate all previously issued JWTs.
        user.tokenVersion += 1

        appUserRepository.save(user)

        return "Password reset successful"
    }
}