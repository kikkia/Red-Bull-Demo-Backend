package com.demo.service

import com.demo.db.entities.UserEntity
import com.demo.db.entities.VideoEntity
import com.demo.db.entities.VideoRatingEntity
import com.demo.db.repositories.RatingRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
open class RatingService(private val ratingRepository: RatingRepository) {

    open fun getForVideoByUser(video: VideoEntity, user: UserEntity) : Optional<VideoRatingEntity> {
        return ratingRepository.findByUserAndVideo(user, video)
    }

    open fun save(rating: VideoRatingEntity) : VideoRatingEntity {
        return ratingRepository.save(rating)
    }

    open fun getForVideo(video: VideoEntity) : List<VideoRatingEntity> {
        return ratingRepository.findByVideo(video)
    }
}
