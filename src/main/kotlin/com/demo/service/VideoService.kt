package com.demo.service

import com.demo.api.entities.Rating
import com.demo.api.entities.Video
import com.demo.api.mappers.VideoMapper
import com.demo.db.entities.VideoEntity
import com.demo.db.entities.VideoRatingEntity
import com.demo.db.repositories.VideoRepository
import com.demo.exceptions.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.streams.toList

@Service
open class VideoService(private val videoRepository: VideoRepository,
                        private val userService: UserService,
                        private val ratingService: RatingService) {

    open fun newVideo(video: Video) : VideoEntity {
        val entity = VideoEntity()
        entity.id = video.id
        entity.description = video.description
        entity.title = video.title
        entity.mediaType = video.mediaType
        entity.contentUrl = video.contentUrl
        entity.previewUrl = video.previewUrl
        entity.source = video.source
        return videoRepository.save(entity)
    }

    open fun getTop10() : List<Video> {
        return videoRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "rating"))).stream().map { VideoMapper.map(it) }.toList()
    }

    open fun get(page: Pageable) : List<Video> {
        return videoRepository.findAll(page).map { VideoMapper.map(it) }.toList()
    }

    open fun getById(videoId: String) : Video {
        return VideoMapper.map(getEntityById(videoId))
    }

    open fun getEntityById(videoId: String) : VideoEntity {
        val opt = videoRepository.findById(videoId)
        if (opt.isEmpty) {
            throw NotFoundException();
        }
        return opt.get()
    }

    open fun getRating(videoId: String, username: String) : Rating {
        val vid = getEntityById(videoId)
        val user = userService.getByUsername(username)
        val ratingOpt = ratingService.getForVideoByUser(vid, user)
        return if(ratingOpt.isPresent) Rating(videoId, ratingOpt.get().rating) else Rating(videoId, 0)
    }

    open fun rateVideo(videoId: String, username: String, score: Int) : Video {
        val vid = getEntityById(videoId)
        val user = userService.getByUsername(username)
        val ratingOpt = ratingService.getForVideoByUser(vid, user)
        val rating = VideoRatingEntity()
        rating.id = if (ratingOpt.isPresent) ratingOpt.get().id else 0
        rating.user = user
        rating.video = vid
        rating.rating = score
        ratingService.save(rating)

        return VideoMapper.map(updateRating(vid))
    }

    private fun updateRating(videoEntity: VideoEntity) : VideoEntity {
        val rating = ratingService.getForVideo(videoEntity).asSequence().map { it.rating }.average()
        videoEntity.rating = rating
        return videoRepository.save(videoEntity)
    }
}