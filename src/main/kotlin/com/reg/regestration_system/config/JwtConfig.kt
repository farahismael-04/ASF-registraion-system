package com.reg.regestration_system.config

import com.reg.regestration_system.repository.AppUserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import com.nimbusds.jose.jwk.source.ImmutableSecret
import javax.crypto.spec.SecretKeySpec

@Configuration
class JwtConfig(
    @Value("\${app.jwt.secret}")
    private val jwtSecret: String
) {

    private fun secretKey(): SecretKeySpec {
        val keyBytes = jwtSecret.toByteArray(Charsets.UTF_8)

        require(keyBytes.size >= 32) {
            "JWT secret must be at least 32 bytes"
        }

        return SecretKeySpec(keyBytes, "HmacSHA256")
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        return NimbusJwtEncoder(
            ImmutableSecret(secretKey())
        )
    }

    @Bean
    fun jwtDecoder(
        appUserRepository: AppUserRepository
    ): JwtDecoder {

        val decoder = NimbusJwtDecoder
            .withSecretKey(secretKey())
            .macAlgorithm(MacAlgorithm.HS256)
            .build()

        val versionValidator = OAuth2TokenValidator<Jwt> { jwt ->

            val username = jwt.subject
            val tokenVersion =
                (jwt.claims["tokenVersion"] as? Number)?.toInt()

            val user = username?.let {
                appUserRepository.findByUsername(it)
            }

            if (
                user != null &&
                tokenVersion != null &&
                user.tokenVersion == tokenVersion
            ) {
                OAuth2TokenValidatorResult.success()
            } else {
                OAuth2TokenValidatorResult.failure(
                    OAuth2Error(
                        "invalid_token",
                        "Token has been revoked",
                        null
                    )
                )
            }
        }

        decoder.setJwtValidator(
            DelegatingOAuth2TokenValidator(
                JwtValidators.createDefaultWithIssuer(
                    "asf-automation-supplier"
                ),
                versionValidator
            )
        )

        return decoder
    }
}