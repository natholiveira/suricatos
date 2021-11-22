package com.fiap.suricatos.service.impl

import com.fiap.cyrela.exception.NotFoundExeption
import com.fiap.suricatos.data.UserDataDetails
import com.fiap.suricatos.repository.UserRepository
import com.fiap.suricatos.request.LoginRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UserDetailsServiceImpl(
        private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        userRepository.findByEmail(username)?.let { user ->
            val loginRequest = LoginRequest(username = user.email!!, password = user.password!!)
            return UserDataDetails(loginRequest)
        } ?: throw NotFoundExeption("User with email $username not found")
    }
}