package com.fiap.cyrela.exception

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fiap.suricatos.exception.ApiError

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class ErrorResponse(
        val type: ApiError,
        val message: Any
)