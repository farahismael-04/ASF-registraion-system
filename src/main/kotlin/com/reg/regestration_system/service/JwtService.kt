package com.reg.regestration_system.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class JwtService(
    private val jwtEncoder: JwtEncoder,

    @Value("\${app.jwt.expiration-ms}")
    private val expirationMs: Long
) {
    fun generateToken(
        userId: Long,
        username: String,
        tokenVersion: Int
    ): String {
        val now = Instant.now()

        val claims = JwtClaimsSet.builder()
            .issuer("asf-automation-supplier")
            .issuedAt(now)
            .expiresAt(now.plusMillis(expirationMs))
            .subject(username)
            .claim("userId", userId)
            .claim("tokenVersion", tokenVersion)
            .build()

        val header = JwsHeader.with(MacAlgorithm.HS256).build()

        return jwtEncoder.encode(
            JwtEncoderParameters.from(header, claims)
        ).tokenValue
    }

    fun getExpirationSeconds(): Long {
        return expirationMs / 1000
    }
}