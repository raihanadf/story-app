package com.raihan.story.data.repository.story

import android.net.Uri
import androidx.core.net.toFile
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.data.model.dto.story.StoryAddResponse
import com.raihan.story.data.model.dto.story.StoryAllResponse
import com.raihan.story.data.network.StoryService
import com.raihan.story.utils.ext.reduceFileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

interface StoryRepository {
    fun getAllStories(): Flow<ApiStatus<StoryAllResponse>>
    fun getAllStoriesWithLocation(): Flow<ApiStatus<StoryAllResponse>>
    fun uploadStory(
        imageUri: Uri, description: String
    ): Flow<ApiStatus<StoryAddResponse>>
}

class StoryRepositoryImpl(private val api: StoryService) : StoryRepository {
    override fun getAllStories(): Flow<ApiStatus<StoryAllResponse>> = flow {
        try {
            emit(ApiStatus.Loading)
            val response = api.getAll()
            if (!response.error) {
                emit(ApiStatus.Success(response))
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiStatus.Error(e.message.toString()))
        }
    }

    override fun getAllStoriesWithLocation(): Flow<ApiStatus<StoryAllResponse>> =
        flow {
            try {
                emit(ApiStatus.Loading)
                val response = api.getAllWithLocation()
                if (!response.error) {
                    emit(ApiStatus.Success(response))
                } else {
                    throw Exception()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiStatus.Error(e.message.toString()))
            }
        }

    override fun uploadStory(
        imageUri: Uri, description: String
    ): Flow<ApiStatus<StoryAddResponse>> = flow {
        try {
            emit(ApiStatus.Loading)

            val photo = imageUri.toFile().reduceFileImage()
            val requestImageFile = photo.asRequestBody("image/*".toMediaType())
            val descriptionBody =
                description.toRequestBody("text/plain".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo", photo.name, requestImageFile
            )

            val response = api.upload(multipartBody, descriptionBody)

            if (!response.error) {
                emit(ApiStatus.Success(response))
            } else {
                emit(ApiStatus.Error(response.message))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiStatus.Error(e.message.toString()))
        }
    }

}