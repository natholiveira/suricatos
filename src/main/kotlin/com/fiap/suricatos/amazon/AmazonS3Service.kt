package com.fiap.suricatos.amazon

import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.fiap.suricatos.amazon.client.AmazonClient
import com.fiap.suricatos.exception.Base64Exception
import com.fiap.suricatos.util.Base64FileGenerator
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File


@Service
class AmazonS3Service(
        private val base64FileGenerator: Base64FileGenerator
) : AmazonClient() {

    fun uploadImageToAmazon(multipartFile: MultipartFile): String {
        val validExtensions = arrayListOf("jpeg", "jpg", "png")

        val extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename())

        if (!validExtensions.contains(extension)) throw Base64Exception()

        val file: File = base64FileGenerator.convertMultipartToFile(multipartFile)

        val fileName = base64FileGenerator.generateFileName(multipartFile)

        getClient().putObject(PutObjectRequest(getBucketName(), fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead))

        return "${getUrl()}/$fileName";
    }

}