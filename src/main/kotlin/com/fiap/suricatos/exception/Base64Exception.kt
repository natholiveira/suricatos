package com.fiap.suricatos.exception

import org.springframework.http.HttpStatus

class Base64Exception(cause: Throwable? = null): ApiException(cause) {
    override fun httpStatus(): HttpStatus = HttpStatus.UNPROCESSABLE_ENTITY
    override fun apiError(): ApiError = ApiError.UNPROCESSABLE_IMAGE
    override fun userResponseMessage(): String = "Image is Invalid"
}