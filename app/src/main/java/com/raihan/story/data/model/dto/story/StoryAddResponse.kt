package com.raihan.story.data.model.dto.story

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class StoryAddResponse(

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)
