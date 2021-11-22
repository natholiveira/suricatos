package com.fiap.suricatos.util.impl

import com.fiap.suricatos.exception.Base64Exception
import com.fiap.suricatos.util.Base64FileGenerator
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*


@Service
class Base64FileGeneratorImpl : Base64FileGenerator {
    override fun convertMultipartToFile(file: MultipartFile): File =
        try {
            val convFile = File(System.getProperty("java.io.tmpdir") + "/" + file.originalFilename)
            file.transferTo(convFile)

            convFile
        } catch (ex: Exception) {
            throw Base64Exception(ex)
        }

    override fun generateFileName(multipartFile: MultipartFile): String? {
        return Date().time.toString() + "-" + Objects.requireNonNull(multipartFile.originalFilename)!!.replace(" ", "_")
    }
}