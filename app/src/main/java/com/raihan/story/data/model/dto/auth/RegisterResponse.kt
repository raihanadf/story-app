package com.raihan.story.data.model.dto.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse(

    @Json(name = "error")
    val error: Boolean,

    @Json(name = "message")
    val message: String
)
