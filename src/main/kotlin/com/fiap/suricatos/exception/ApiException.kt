package com.fiap.suricatos.exception

import com.fiap.cyrela.exception.ErrorResponse
import org.springframework.http.HttpStatus

abstract class ApiException : Exception {

    constructor(cause: Throwable?) : super(cause)
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable?): super(message, cause)

    abstract fun httpStatus(): HttpStatus
    abstract fun apiError(): ApiError
    abstract fun userResponseMessage(): String?

    fun createErrorResponse(): ErrorResponse {
        return ErrorResponse(
                apiError(),
                userResponseMessage()!!
        )
    }
}