package com.raihan.story.data.model.dto.story

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class StoryDetailResponse(

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null,

	@Json(name="story")
	val story: Story? = null
)

@JsonClass(generateAdapter = true)
data class Story(

	@Json(name="photoUrl")
	val photoUrl: String? = null,

	@Json(name="createdAt")
	val createdAt: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="lon")
	val lon: Any? = null,

	@Json(name="id")
	val id: String? = null,

	@Json(name="lat")
	val lat: Any? = null
)
