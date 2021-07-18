package com.demo.config.security

import com.demo.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(private val tokenService: TokenService) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        // Allow login and register endpoints when not authed
        if (httpRequest.requestURI.startsWith("/api/auth/")) {
            chain.doFilter(request, response)
            return
        }

        val cookie = getTokenCookie(httpRequest)
        if (cookie.isEmpty) {
            // No token cookie, 401
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing Token cookie")
            return
        }
        val token = cookie.get().value
        if (!tokenService.validateToken(token)) {
            // Invalid token, 401
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid Token")
            return
        }

        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(tokenService.getSubjectFromToken(token), token, null)


        chain.doFilter(request, response)
    }

    private fun getTokenCookie(req: HttpServletRequest) : Optional<Cookie> {
        if (req.cookies == null) {
            return Optional.empty()
        }

        for (cookie in req.cookies) {
            if (cookie.name == "token") {
                return Optional.of(cookie)
            }
        }
        return Optional.empty()
    }
}