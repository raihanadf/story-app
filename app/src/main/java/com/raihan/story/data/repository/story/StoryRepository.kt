package com.raihan.story.data.repository.story

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.data.model.dto.story.Story
import com.raihan.story.data.model.dto.story.StoryAddResponse
import com.raihan.story.data.model.dto.story.StoryAllResponse
import com.raihan.story.data.network.StoryService
import com.raihan.story.data.paging.StoryPagingSource
import com.raihan.story.utils.ext.reduceFileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

interface StoryRepository {
    fun getAllStories(): LiveData<PagingData<Story>>
    fun getAllStoriesWithLocation(): Flow<ApiStatus<StoryAllResponse>>
    fun uploadStory(
        imageUri: Uri, description: String, lat: Double, long: Double
    ): Flow<ApiStatus<StoryAddResponse>>
}

class StoryRepositoryImpl(private val api: StoryService) : StoryRepository {
    override fun getAllStories(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(api)
            }
        ).liveData
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
        imageUri: Uri,
        description: String,
        lat: Double,
        long: Double
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

            val response = api.upload(multipartBody, descriptionBody, lat, long)

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