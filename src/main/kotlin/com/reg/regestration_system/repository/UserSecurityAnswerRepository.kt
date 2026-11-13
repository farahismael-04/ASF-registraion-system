package com.reg.regestration_system.repository

import com.reg.regestration_system.models.UserSecurityAnswer
import org.springframework.data.jpa.repository.JpaRepository

interface UserSecurityAnswerRepository :
    JpaRepository<UserSecurityAnswer, Long> {

    fun findAllByUserUserId(userId: Long): List<UserSecurityAnswer>
}