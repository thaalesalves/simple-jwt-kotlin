package com.signicat.interview.adapters.rest.filter

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
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


class AuthorizationFilter(authenticationManager: AuthenticationManager?) :
    BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse, chain: FilterChain
    ) {
        try {
            val header = request.getHeader(HEADER_NAME)
            if (header != null) {
                val authentication = authenticate(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } finally {
            chain.doFilter(request, response)
        }
    }

    private fun authenticate(request: HttpServletRequest): UsernamePasswordAuthenticationToken {

        return Optional.ofNullable(request.getHeader(HEADER_NAME))
            .filter { token: String -> !token.isBlank() }
            .map { token: String? ->
                Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(KEY.toByteArray()))
                    .build()
                    .parseClaimsJws(token)
                    .body
            }
            .map { claim: Claims? ->
                UsernamePasswordAuthenticationToken(
                    claim,
                    null,
                    emptyList()
                )
            }
            .orElseThrow { RuntimeException("Error during authentication") }
    }

    companion object {
        private const val HEADER_NAME = "Authorization"
        private const val KEY = "q3t6w9z\$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh"
    }
}
