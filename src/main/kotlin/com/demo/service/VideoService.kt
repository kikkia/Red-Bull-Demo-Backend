package com.demo.service

import com.demo.api.entities.Video
import com.demo.api.mappers.VideoMapper
import com.demo.db.entities.VideoEntity
import com.demo.db.repositories.VideoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.streams.toList

@Service
open class VideoService(private val videoRepository: VideoRepository) {

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
}