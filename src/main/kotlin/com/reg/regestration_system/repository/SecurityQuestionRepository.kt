package com.reg.regestration_system.repository
import com.reg.regestration_system.models.SecurityQuestion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SecurityQuestionRepository :
    JpaRepository<SecurityQuestion, Long>