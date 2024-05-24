package com.raihan.story.data.model.dto.story

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class StoryAllResponse(

	@Json(name="listStory")
	val listStory: List<ListStoryItem?>? = null,

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)

@JsonClass(generateAdapter = true)
data class ListStoryItem(

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
