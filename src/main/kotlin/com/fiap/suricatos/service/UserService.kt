package com.fiap.suricatos.service

import com.fiap.suricatos.model.User
import com.fiap.suricatos.request.UserRequest
import com.fiap.suricatos.response.UserResponse
import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun create(multipartFile: MultipartFile, userRequest: UserRequest): UserResponse?
    fun getUserResponse(userId: Long): UserResponse
    fun getUser(userId: Long): User?
    fun update(multipartFile: MultipartFile, userId: Long, userRequest: UserRequest): UserResponse

}