package com.fiap.suricatos.application

import com.fiap.cyrela.exception.ErrorResponse
import com.fiap.suricatos.exception.ApiException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ApiException::class)
    fun handleValidationError(ex: ApiException): ResponseEntity<ErrorResponse> =
            ResponseEntity.status(ex.httpStatus()).body(ex.createErrorResponse())
}