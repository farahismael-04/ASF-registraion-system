package com.reg.regestration_system.models
import jakarta.persistence.*

@Entity
@Table(
    name = "user_security_answers",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_user_security_question",
            columnNames = ["user_id", "question_id"]
        )
    ]
)
class UserSecurityAnswer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "security_answer_id")
    var securityAnswerId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: AppUser,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    var question: SecurityQuestion,

    @Column(name = "answer_hash", nullable = false, length = 255)
    var answerHash: String
)
