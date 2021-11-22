package com.fiap.suricatos.exception

import org.springframework.http.HttpStatus

class DuplicatedUserException (message: String): ApiException(message) {
    override fun httpStatus(): HttpStatus = HttpStatus.CONFLICT
    override fun apiError(): ApiError = ApiError.DUPLICATED_EMAIL
    override fun userResponseMessage(): String? = message
}