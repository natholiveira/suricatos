package com.fiap.suricatos.request

import com.fiap.suricatos.enum.PostType
import java.io.File
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PostRequest (
        val slug: String? = null,

        @field:NotNull
        @field:NotBlank
        val title: String? = null,

        @field:NotNull
        @field:NotBlank
        val description: String? = null,

        @field:NotNull
        val userId: Long? = null,

        @field:NotNull
        val address: AddressRequest? = null,

        @field:NotNull
        val type: PostType? = null
)