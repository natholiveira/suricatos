package com.fiap.suricatos.exception

import org.springframework.http.HttpStatus

class UnauthorizedException (message: String): ApiException(message) {
    override fun httpStatus(): HttpStatus = HttpStatus.UNAUTHORIZED
    override fun apiError(): ApiError = ApiError.UNAUTHORIZED
    override fun userResponseMessage(): String? = message
}