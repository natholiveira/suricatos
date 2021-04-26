package com.fiap.suricatos.util

import org.springframework.web.multipart.MultipartFile

interface Base64FileGenerator {
    fun generateFile(file: MultipartFile): String?
}