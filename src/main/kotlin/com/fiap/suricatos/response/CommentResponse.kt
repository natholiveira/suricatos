package com.fiap.suricatos.response

import com.fiap.suricatos.model.User
import java.time.OffsetDateTime

data class CommentResponse(
        val id: Long,
        val user: User,
        val message: String,
        val createdAt: OffsetDateTime
)