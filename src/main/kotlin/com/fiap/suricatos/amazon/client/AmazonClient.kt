package com.fiap.suricatos.amazon.client

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct


@Service
class AmazonClient {

    private lateinit var amazonS3: AmazonS3

    @Value("\${amazon.s3.endpoint}")
    private val url: String? = null

    @Value("\${amazon.s3.bucket-name}")
    private val bucketName: String? = null

    @Value("\${amazon.s3.access-key}")
    private val accessKey: String? = null

    @Value("\${amazon.s3.secret-key}")
    private val secretKey: String? = null

    @PostConstruct
    fun init() {
        val credentials = BasicAWSCredentials(accessKey, secretKey)

        amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.SA_EAST_1)
                .withCredentials(AWSStaticCredentialsProvider(credentials))
                .build()
    }

    fun getClient() = amazonS3

    fun getBucketName() = bucketName

    fun getUrl() = url
}