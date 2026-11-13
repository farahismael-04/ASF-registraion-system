package com.reg.regestration_system.repository
import com.reg.regestration_system.models.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long> {

    fun findByUsername(username: String): AppUser?

    fun existsByUsername(username: String): Boolean
}