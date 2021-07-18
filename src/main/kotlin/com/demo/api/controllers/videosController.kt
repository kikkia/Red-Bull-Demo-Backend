package com.demo.api.controllers

import com.demo.api.entities.Video
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.demo.service.VideoService
import org.springframework.data.domain.PageRequest

@RestController
@RequestMapping("/api/videos")
class videosController(private val videoService: VideoService) {

    @PostMapping("/create")
    fun createVideos(@RequestBody videos: List<Video>) : ResponseEntity<String> {
        for (video in videos) {
            videoService.newVideo(video)
        }
        return ResponseEntity.ok("Successfully added ${videos.size} new videos.")
    }

    @GetMapping
    fun getVideos(@RequestParam limit: Int, @RequestParam page: Int) : ResponseEntity<List<Video>> {
        return ResponseEntity.ok(videoService.get(PageRequest.of(page, limit)))
    }

    @GetMapping("/top")
    fun getTop10() : ResponseEntity<List<Video>> {
        return ResponseEntity.ok(videoService.getTop10())
    }
}