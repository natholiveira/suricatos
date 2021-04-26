package com.fiap.suricatos.request

import com.fiap.suricatos.enum.UserType
import java.util.Date
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserRequest (
        @field:NotNull
        @field:NotBlank
        val name: String? = null,

        @field:NotNull
        val birthday: Date? = null,

        @field:NotNull
        val type: UserType? = null,

        @field:NotNull
        val biography: String? = null,

        @field:NotNull
        val image: String,

        @field:NotNull
        val phone: PhoneRequest,

        @field:NotNull
        val addressRequest: AddressRequest
)