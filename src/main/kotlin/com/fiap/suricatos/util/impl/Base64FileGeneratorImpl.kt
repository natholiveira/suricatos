package com.fiap.suricatos.util.impl

import com.fiap.suricatos.exception.Base64Exception
import com.fiap.suricatos.util.Base64FileGenerator
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class Base64FileGeneratorImpl : Base64FileGenerator {
    override fun generateFile(file: MultipartFile): String? =
        try {
            Base64.getEncoder().encodeToString(file.bytes)
        } catch (ex: Exception) {
            throw Base64Exception(ex)
        }
}