package com.reg.regestration_system.service

import com.reg.regestration_system.dto.request.RegisterRequest
import com.reg.regestration_system.dto.response.RegisterResponse
import com.reg.regestration_system.models.AppUser
import com.reg.regestration_system.models.UserSecurityAnswer
import com.reg.regestration_system.repository.AppUserRepository
import com.reg.regestration_system.repository.SecurityQuestionRepository
import com.reg.regestration_system.repository.UserSecurityAnswerRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.reg.regestration_system.exception.UsernameAlreadyExistsException

@Service
class RegistrationService(
	private val appUserRepository: AppUserRepository,
	private val securityQuestionRepository: SecurityQuestionRepository,
	private val userSecurityAnswerRepository: UserSecurityAnswerRepository,
	private val passwordEncoder: PasswordEncoder
) {

	@Transactional
	fun register(request: RegisterRequest): RegisterResponse {

		// 1. Normalize username
		val username = request.username.trim()

		// 2. Check if username already exists
		if (appUserRepository.existsByUsername(username)) {
			throw UsernameAlreadyExistsException()
		}

		// 3. Check exactly three different questions
		val questionIds = request.securityAnswers.map { it.questionId }

		if (questionIds.size != 3 || questionIds.distinct().size != 3) {
			throw IllegalArgumentException(
				"You must select exactly three different security questions"
			)
		}

		// 4. Retrieve selected questions
		val questions = securityQuestionRepository.findAllById(questionIds)
			.associateBy { it.questionId }

		if (questions.size != 3) {
			throw IllegalArgumentException(
				"One or more security questions do not exist"
			)
		}

		// 5. Hash password
		val hashedPassword = requireNotNull(
			passwordEncoder.encode(request.password)
		)

		// 6. Create and save user
		val user = AppUser(
			username = username,
			passwordHash = hashedPassword
		)

		val savedUser = appUserRepository.save(user)

		// 7. Hash security answers
		val securityAnswers = request.securityAnswers.map { answerRequest ->

			val question = questions[answerRequest.questionId]
				?: throw IllegalArgumentException("Invalid security question")

			val normalizedAnswer = answerRequest.answer.trim().lowercase()

			val hashedAnswer = requireNotNull(
				passwordEncoder.encode(normalizedAnswer)
			)

			UserSecurityAnswer(
				user = savedUser,
				question = question,
				answerHash = hashedAnswer
			)
		}

		// 8. Save all answers
		userSecurityAnswerRepository.saveAll(securityAnswers)

		// 9. Return response
		return RegisterResponse(
			userId = requireNotNull(savedUser.userId),
			username = savedUser.username,
			message = "Registration successful"
		)
	}
}
