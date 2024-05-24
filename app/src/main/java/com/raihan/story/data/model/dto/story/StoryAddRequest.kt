package com.raihan.story.data.model.dto.story

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class StoryAddRequest (
    @Part
    val photo: MultipartBody.Part,

    @Part("description")
    val description: RequestBody

)