package com.fiap.suricatos.service

import com.fiap.suricatos.enum.Status
import com.fiap.suricatos.model.PostReply
import com.fiap.suricatos.request.PostReplyRequest
import com.fiap.suricatos.request.PostRequest
import com.fiap.suricatos.response.PostResponse
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile

interface PostService {
    fun replyPost(postReplyRequest: PostReplyRequest, token: String): PostReply
    fun getPostResponse(postId: Long): PostResponse
    fun createPost(postRequest: PostRequest, images: List<MultipartFile>, token: String): PostResponse
    fun getAllPostByUserAndStatus(token: String, status: Status, pageable: Pageable): List<PostResponse>
    fun getAll(pageable: Pageable): List<PostResponse>
    fun getAllByStatusAndCity(city: String, status: Status, pageable: Pageable): List<PostResponse>
    fun delete(postId: Long)
    fun getAllCategory(): List<String>
    fun like(postId: Long, like: Boolean)
    fun update(postId: Long, postRequest: PostRequest, images: List<MultipartFile>): PostResponse
}