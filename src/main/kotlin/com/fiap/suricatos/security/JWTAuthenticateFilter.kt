package com.fiap.suricatos.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fiap.suricatos.data.UserDataDetails
import com.fiap.suricatos.request.LoginRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws


class JWTAuthenticateFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse?): Authentication? {
        return try {
            val usuario =  jacksonObjectMapper()
                    .readValue(request.inputStream, LoginRequest::class.java)

            this.authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(
                    usuario.username,
                    usuario.password,
                    ArrayList()
            ))
        } catch (e: IOException) {
            throw RuntimeException("Falha ao autenticar usuario", e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest?,
                                          response: HttpServletResponse,
                                          chain: FilterChain?,
                                          authResult: Authentication) {
        val usuarioData = authResult.principal as UserDataDetails

        val token = JWT.create().withSubject(usuarioData.username)
                .withExpiresAt(Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(TOKEN_SENHA))

        response.writer.write(token)
        response.writer.flush()
    }

    companion object {
        const val TOKEN_EXPIRACAO = 600_000
        const val TOKEN_SENHA = "463408a1-54c9-4307-bb1c-6cced559f5a7"
    }
}