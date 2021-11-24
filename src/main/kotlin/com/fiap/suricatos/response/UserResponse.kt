package com.fiap.suricatos.response

import com.fiap.suricatos.model.User
import com.fiap.suricatos.model.UserPhone

data class UserResponse (
        val user: User,
        val phone: UserPhone?,
        val image: String?,
)