package com.reg.regestration_system.models

import jakarta.persistence.*

@Entity
@Table(name = "security_question_bank")
class SecurityQuestion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    var questionId: Long? = null,

    @Column(name = "question_text", nullable = false, unique = true, length = 255)
    var questionText: String
)
