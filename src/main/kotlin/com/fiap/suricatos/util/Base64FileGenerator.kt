package com.fiap.suricatos.util

import org.springframework.web.multipart.MultipartFile
import java.io.File

interface Base64FileGenerator {
    fun convertMultipartToFile(file: MultipartFile): File
    fun generateFileName(multipartFile: MultipartFile): String?
}