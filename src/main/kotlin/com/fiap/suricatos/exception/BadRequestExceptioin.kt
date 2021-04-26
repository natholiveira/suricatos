package com.fiap.suricatos.exception

import org.springframework.http.HttpStatus

class BadRequestExceptioin (cause: Throwable? = null): ApiException(cause) {
    override fun httpStatus(): HttpStatus = HttpStatus.BAD_REQUEST
    override fun apiError(): ApiError = ApiError.BAD_REQUEST
    override fun userResponseMessage(): Any = "Malformed body"
}