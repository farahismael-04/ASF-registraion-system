package com.reg.regestration_system.models

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "app_user")
class AppUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var userId: Long? = null,

    @Column(name = "username", nullable = false, unique = true, length = 50)
    var username: String,

    @Column(name = "password_hash", nullable = false, length = 255)
    var passwordHash: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "last_login_at")
    var lastLoginAt: OffsetDateTime? = null,
    @Column(name = "token_version", nullable = false)
    var tokenVersion: Int = 0

)


