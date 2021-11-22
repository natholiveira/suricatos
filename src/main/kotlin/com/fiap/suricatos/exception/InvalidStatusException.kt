package com.fiap.suricatos.exception

import org.springframework.http.HttpStatus

class InvalidStatusException (message: String): ApiException(message) {
    override fun httpStatus(): HttpStatus = HttpStatus.UNPROCESSABLE_ENTITY
    override fun apiError(): ApiError = ApiError.INVALID_STATUS
    override fun userResponseMessage(): String = "Invalid Status"
}