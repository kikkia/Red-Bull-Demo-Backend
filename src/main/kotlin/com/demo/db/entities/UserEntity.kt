package com.demo.db.entities

import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    @Column(nullable = false)
    val username: String,
    @Column(nullable = false)
    var passwordHash: String
)