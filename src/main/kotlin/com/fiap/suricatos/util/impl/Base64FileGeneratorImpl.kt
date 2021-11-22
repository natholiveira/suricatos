package com.fiap.suricatos.util.impl

import com.fiap.suricatos.exception.Base64Exception
import com.fiap.suricatos.util.Base64FileGenerator
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*


@Service
class Base64FileGeneratorImpl : Base64FileGenerator {
    override fun convertMultipartToFile(file: MultipartFile): File =
        try {
            val file = File(Objects.requireNonNull(file.originalFilename))
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(file.readBytes())
            fileOutputStream.close()

            file
        } catch (ex: Exception) {
            throw Base64Exception(ex)
        }

    override fun generateFileName(multipartFile: MultipartFile): String? {
        return Date().time.toString() + "-" + Objects.requireNonNull(multipartFile.originalFilename)!!.replace(" ", "_")
    }
}