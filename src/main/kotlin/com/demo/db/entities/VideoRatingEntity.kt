package com.demo.db.entities

import javax.persistence.*

@Entity
@Table(name = "video_rating")
class VideoRatingEntity(
    @Id
    var id: Int,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,
    @ManyToOne
    @JoinColumn(name = "video_id")
    val video: VideoEntity,
    @Column(nullable = false)
    var rating: Int
)