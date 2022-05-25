package com.signicat.interview.adapters.rest.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.signicat.interview.application.service.UserService
import com.signicat.interview.domain.model.Subject
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.security.Key
import java.util.*
import javax.naming.AuthenticationException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthenticationFilter(
    private val userService: UserService,
    private val authManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter(authManager) {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
        return try {
            val subject = ObjectMapper().readValue(req.inputStream, Subject::class.java)
            val tokenData = UsernamePasswordAuthenticationToken(
                subject.username, subject.password
            )
            authManager.authenticate(tokenData)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain,
        auth: Authentication
    ) {
        val subject = userService.retrieveByUsername((auth.principal as User).username)
        val exp = Date(System.currentTimeMillis() + EXPIRATION_TIME)
        val key: Key = Keys.hmacShaKeyFor(KEY.toByteArray())
        val token = Jwts.builder()
            .claim("sub", subject.id)
            .claim("username", subject.username)
            .claim("groups", subject.groups)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(exp)
            .compact()
        res.addHeader("token", token)
    }

    companion object {
        private const val KEY = "q3t6w9z\$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh"
        private const val EXPIRATION_TIME = 1000L * 60 * 30
    }
}
