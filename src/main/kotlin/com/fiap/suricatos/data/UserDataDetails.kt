package com.fiap.suricatos.data

import com.fiap.suricatos.request.LoginRequest
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDataDetails(loginRequest: LoginRequest) : UserDetails {

    private var loginRequest: LoginRequest? = loginRequest

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return arrayListOf()
    }

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = loginRequest?.username ?: ""

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = loginRequest?.password ?: ""

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}