package com.demo.service

import com.demo.db.entities.UserEntity
import com.demo.db.repositories.UserRepository
import com.demo.exceptions.LoginException
import com.demo.exceptions.UsernameTakenException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
open class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    open fun create(username: String, pass: String) : String {
        if (userRepository.existsByUsername(username)) {
            throw UsernameTakenException()
        }

        val user = UserEntity()
        user.username = username
        user.passwordHash = passwordEncoder.encode(pass)
        userRepository.save(user)

        return username
    }

    open fun canLogin(username: String, pass: String) : Boolean {
        if (!userRepository.existsByUsername(username)) {
            return false
        }

        val user = userRepository.findByUsername(username)
        if (!passwordEncoder.matches(pass, user.passwordHash)) {
            return false
        }
        return true
    }

    open fun getByUsername(username: String) : UserEntity {
        return userRepository.findByUsername(username)
    }
}