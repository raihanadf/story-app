package com.raihan.story.data.repository.story

import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.data.model.dto.story.StoryAddRequest
import com.raihan.story.data.model.dto.story.StoryAddResponse
import com.raihan.story.data.model.dto.story.StoryAllResponse
import com.raihan.story.data.network.StoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface StoryRepository {
    fun getAllStories(): Flow<ApiStatus<StoryAllResponse>>
    fun addStory(story: StoryAddRequest): Flow<ApiStatus<StoryAddResponse>>
}

class StoryRepositoryImpl(private val api: StoryService) : StoryRepository {
    override fun getAllStories(): Flow<ApiStatus<StoryAllResponse>> = flow {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiStatus.Error(e.message.toString()))
        }
    }

    override fun addStory(story: StoryAddRequest): Flow<ApiStatus<StoryAddResponse>> =
        flow {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiStatus.Error(e.message.toString()))
            }
        }
}