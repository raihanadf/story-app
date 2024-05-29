package com.raihan.story.data.model.dto.story

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class StoryAllResponse(

    @Json(name = "listStory")
    val listStory: List<Story>,

    @Json(name = "error")
    val error: Boolean,

    @Json(name = "message")
    val message: String
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Story(

    @Json(name = "photoUrl")
    val photoUrl: String? = null,

    @Json(name = "createdAt")
    val createdAt: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "lon")
    val lon: Double? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "lat")
    val lat: Double? = null
) : Parcelable
