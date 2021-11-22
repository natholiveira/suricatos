package com.fiap.suricatos.service.impl

import com.fiap.suricatos.service.ChiperService
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*


@Service
class ChipherServiceImpl : ChiperService {

    override fun encrypted(text: String): String {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        val hash: ByteArray = digest.digest(text.toByteArray(StandardCharsets.UTF_8))

        return Base64.getEncoder().encodeToString(hash)
    }

}