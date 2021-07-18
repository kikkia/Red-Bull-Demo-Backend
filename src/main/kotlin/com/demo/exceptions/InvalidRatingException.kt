package com.demo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.BAD_REQUEST, reason = "Ratings must be between 1 and 5")
class InvalidRatingException : RuntimeException() {
}