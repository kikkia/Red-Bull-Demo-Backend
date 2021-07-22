package com.demo.api.controllers

import com.demo.db.entities.UserEntity
import com.demo.exceptions.BadRequestException
import com.demo.exceptions.LoginException
import com.demo.service.TokenService
import com.demo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/auth")
class AuthController(private val userService: UserService, private val tokenService: TokenService) {

    @PostMapping("/login")
    fun login(@RequestParam username: String, @RequestParam pass: String, response: HttpServletResponse) : ResponseEntity<String> {
        if (!userService.canLogin(username, pass)) {
            throw LoginException()
        }
        val user = userService.getByUsername(username)
        setAuthCookies(user, response)
        return ResponseEntity.ok("success");
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse) : ResponseEntity<String> {
        setLogoutCookies(response)
        return ResponseEntity.ok("success");
    }

    @PostMapping("/register")
    fun register(@RequestParam username: String, @RequestParam pass: String) : ResponseEntity<String> {
        if (username.length < 3 || username.length > 100) {
            throw BadRequestException()
        } else if (pass.length < 3 || pass.length > 200) {
            throw BadRequestException()
        }

        userService.create(username, pass)
        return ResponseEntity.ok(username)
    }

    private fun setAuthCookies(user: UserEntity, response: HttpServletResponse) {
        val cookies = mutableListOf<Cookie>()

        val tokenCookie = Cookie("token", tokenService.generateToken(user))
        tokenCookie.isHttpOnly = true

        val usernameCookie = Cookie("username", user.username)
        cookies.add(tokenCookie)
        cookies.add(usernameCookie)

        for (cookie in cookies) {
            cookie.maxAge = tokenService.JWT_TOKEN_VALIDITY.toInt()
            cookie.path = "/"
            response.addCookie(cookie)
        }
    }

    private fun setLogoutCookies(response: HttpServletResponse) {
        val cookies = mutableListOf<Cookie>()

        val tokenCookie = Cookie("token", "")
        tokenCookie.isHttpOnly = true

        val usernameCookie = Cookie("username", "")
        cookies.add(tokenCookie)
        cookies.add(usernameCookie)

        for (cookie in cookies) {
            cookie.maxAge = tokenService.JWT_TOKEN_VALIDITY.toInt()
            cookie.path = "/"
            response.addCookie(cookie)
        }
    }
}