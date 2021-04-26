package com.fiap.suricatos.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class AddressRequest (
        @field:NotNull
        @field:NotBlank
        val state: String? = null,

        @field:NotNull
        @field:NotBlank
        val number: String? = null,

        @field:NotNull
        @field:NotBlank
        val city: String? = null,

        @field:NotNull
        @field:NotBlank
        val complement: String? = null,

        @field:NotNull
        @field:NotBlank
        val zipCode : String? = null,

        @field:NotNull
        @field:NotBlank
        val street : String? = null,

        @field:NotNull
        @field:NotBlank
        val neighborhood  : String? = null
)