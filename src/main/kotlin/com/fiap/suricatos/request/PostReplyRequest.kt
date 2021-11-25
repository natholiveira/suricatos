package com.fiap.suricatos.request

import com.fiap.suricatos.enum.Status
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PostReplyRequest (
        @field:NotNull
        val postId: Long? = null,

        @field:NotNull
        val externalLink: String? = "",

        @field:NotNull
        val externalProtocal: String? = "",

        @field:NotNull
        val status: Status? = null,

        @field:NotNull
        @field:NotBlank
        val description: String? = null,
)