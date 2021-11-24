package com.fiap.suricatos.service

import com.fiap.suricatos.model.User
import com.fiap.suricatos.request.UserRequest
import com.fiap.suricatos.response.UserResponse
import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun createWithImage(multipartFile: MultipartFile, userRequest: UserRequest): UserResponse?
    fun getUserResponse(userId: Long): UserResponse?
    fun update(token: String, userRequest: UserRequest): UserResponse
    fun findAll(): List<User>
    fun getUserLogged(token: String): UserResponse
    fun getUser(token: String): User
    fun create(userRequest: UserRequest): UserResponse
    fun updateImage(multipartFile: MultipartFile, token: String): UserResponse
}