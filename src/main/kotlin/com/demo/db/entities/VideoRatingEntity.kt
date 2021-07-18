package com.demo.db.entities

import javax.persistence.*

@Entity
@Table(name = "video_rating")
class VideoRatingEntity {
    @Id
    var id: Int = 0
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
    @ManyToOne
    @JoinColumn(name = "video_id")
    var video: VideoEntity? = null
    @Column(nullable = false)
    var rating: Int = 0
}