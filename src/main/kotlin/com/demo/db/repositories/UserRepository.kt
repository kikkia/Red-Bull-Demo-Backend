package com.demo.db.repositories

import com.demo.db.entities.UserEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<UserEntity, Int> {
    fun findByUsername(username: String) : Optional<UserEntity>
}