package com.fiap.suricatos.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CommentRequest(
        @field:NotNull
        @field:NotBlank
        val message: String
)