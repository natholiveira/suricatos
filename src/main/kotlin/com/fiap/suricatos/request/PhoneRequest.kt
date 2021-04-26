package com.fiap.suricatos.request

import com.fiap.suricatos.enum.PhoneType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PhoneRequest (
        @field:NotNull
        @field:NotBlank
        val ddd: String? = null,

        @field:NotNull
        @field:NotBlank
        val number: String? = null,

        @field:NotNull
        val type: PhoneType
)