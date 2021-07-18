package com.demo.db.repositories

import com.demo.db.entities.VideoEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface VideoRepository : PagingAndSortingRepository<VideoEntity, String> {
}