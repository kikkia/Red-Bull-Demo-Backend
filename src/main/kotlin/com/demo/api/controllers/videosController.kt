package com.demo.api.controllers

import com.demo.api.entities.Rating
import com.demo.api.entities.Video
import com.demo.exceptions.InvalidRatingException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.demo.service.VideoService
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder

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

    @PostMapping("/{videoId}/rate")
    fun rateVideo(@PathVariable("videoId") videoId: String, @RequestParam rating: Int) : ResponseEntity<Video> {
        val auth = SecurityContextHolder.getContext().authentication

        if (rating <= 0 || rating > 5) {
            throw InvalidRatingException()
        }

        return ResponseEntity.ok(videoService.rateVideo(videoId, auth.name, rating))
    }

    @GetMapping("/{videoId}/rate")
    fun getMyRating(@PathVariable("videoId") videoId: String) : ResponseEntity<Rating> {
        val auth = SecurityContextHolder.getContext().authentication
        return ResponseEntity.ok(videoService.getRating(videoId, auth.name))
    }
}