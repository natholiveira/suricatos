package com.fiap.suricatos.security

import com.auth0.jwt.JWT

import org.springframework.stereotype.Service

@Service
class JWTDecoder {

    fun decodeJWT(token: String): String {
        val tokenJwt = token.replace(JWTValidatorFilter.ATRIBUTE_PREFIX, "")

        return JWT.decode(tokenJwt).subject
    }
}