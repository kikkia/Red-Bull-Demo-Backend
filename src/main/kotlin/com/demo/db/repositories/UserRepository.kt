package com.demo.db.repositories

import com.demo.db.entities.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Int> {
    fun findByUsername(username: String) : UserEntity
    fun existsByUsername(username: String) : Boolean
}