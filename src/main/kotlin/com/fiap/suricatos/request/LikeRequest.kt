package com.fiap.suricatos.request

import javax.validation.constraints.NotNull

data class LikeRequest (
        @field:NotNull
        val like: Boolean
)