package com.demo.api.entities


data class Video(val id: String,
                 val mediaType: String,
                 val source: String,
                 val title: String,
                 val contentUrl: String,
                 val previewUrl: String) {
    var rating : Double = 0.0
    var description : String = ""
}