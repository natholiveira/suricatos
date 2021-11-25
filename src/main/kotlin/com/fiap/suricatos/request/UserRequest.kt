package com.fiap.suricatos.request

import com.fiap.suricatos.enum.UserType
import java.util.Date
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserRequest (
        @field:NotNull
        @field:NotBlank
        val name: String? = null,

        val birthday: Date? = null,

        @field:NotNull
        val type: UserType? = null,

        val biography: String? = null,

        @field:NotNull
        val phone: PhoneRequest,

        @field:NotNull
        @field:NotBlank
        val email: String,

        @field:NotNull
        @field:NotBlank
        val password: String,

        val address: AddressRequest? = null
)