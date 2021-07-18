package com.demo.db.entities

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    @Column(nullable = false)
    var username: String = ""
    @Column(nullable = false, name = "password_hash")
    var passwordHash: String = ""
}