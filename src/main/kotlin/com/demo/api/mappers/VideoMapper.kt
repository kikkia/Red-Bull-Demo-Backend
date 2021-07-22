package com.demo.api.mappers

import com.demo.api.entities.Video
import com.demo.db.entities.VideoEntity

class VideoMapper {
    companion object {
        fun map(videoEntity: VideoEntity) : Video {
            val vid = Video(videoEntity.id, videoEntity.mediaType, videoEntity.source, videoEntity.title, videoEntity.contentUrl, videoEntity.previewUrl)
            vid.rating = videoEntity.rating
            vid.description = videoEntity.description
            return vid
        }
    }
}