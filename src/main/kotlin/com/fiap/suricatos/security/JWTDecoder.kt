package com.fiap.suricatos.security

import com.auth0.jwt.JWT

import org.springframework.stereotype.Service

@Service
class JWTDecoder {

    fun decodeJWT(token: String) {
        val tokenJwt = token.replace(JWTValidatorFilter.ATRIBUTE_PREFIX, "")

        val teste = JWT.decode(tokenJwt).subject

        print(teste)
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val ATRIBUTE_PREFIX = "Bearer "
    }
}