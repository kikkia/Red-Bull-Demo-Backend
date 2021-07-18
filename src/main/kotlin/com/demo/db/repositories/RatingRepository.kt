package com.demo.db.repositories

import com.demo.db.entities.UserEntity
import com.demo.db.entities.VideoEntity
import com.demo.db.entities.VideoRatingEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RatingRepository : CrudRepository<VideoRatingEntity, Int> {
    fun findByVideo(video: VideoEntity) : List<VideoRatingEntity>
    fun findByUser(user: UserEntity) : List<VideoRatingEntity>
    fun findByUserAndVideo(user: UserEntity, video: VideoEntity) : Optional<VideoRatingEntity>
}