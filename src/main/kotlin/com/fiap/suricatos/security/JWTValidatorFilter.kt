package com.fiap.suricatos.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws


class JWTValidatorFilter(authenticationManager: AuthenticationManager) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val atributo = request.getHeader(AUTHORIZATION)
        if (atributo == null) {
            chain.doFilter(request, response)
            return
        }
        if (!atributo.startsWith(ATRIBUTE_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        val token: String = atributo.replace(ATRIBUTE_PREFIX, "")
        val authenticationToken = getAuthenticationToken(token)
        SecurityContextHolder.getContext().authentication = authenticationToken
        chain.doFilter(request, response)
    }

    private fun getAuthenticationToken(token: String): UsernamePasswordAuthenticationToken? {
        val usuario = JWT.require(Algorithm.HMAC512(JWTAuthenticateFilter.TOKEN_SENHA))
                .build()
                .verify(token)
                .subject

        return UsernamePasswordAuthenticationToken(usuario, null, ArrayList())
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val ATRIBUTE_PREFIX = "Bearer "
    }
}