package com.demo.db.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * JPA wants a default contsructor for some odd reason on this class, so no data class allowed
 */
@Entity
@Table(name = "videos")
class VideoEntity {
    @Id
    var id: String = ""
    @Column(nullable = false, name = "media_type")
    var mediaType: String = ""
    @Column(nullable = false)
    var source: String = ""
    @Column(nullable = false)
    var title: String = ""
    @Column(nullable = false)
    var description: String = ""
    @Column(nullable = false, name = "content_url")
    var contentUrl: String = ""
    @Column(nullable = false, name = "preview_url")
    var previewUrl: String = ""
    @Column
    var rating: Double = 0.0
}