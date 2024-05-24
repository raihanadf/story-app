package com.raihan.story.data.network

import com.raihan.story.data.model.dto.auth.LoginRequest
import com.raihan.story.data.model.dto.auth.LoginResponse
import com.raihan.story.data.model.dto.auth.RegisterRequest
import com.raihan.story.data.model.dto.auth.RegisterResponse
import com.raihan.story.data.model.dto.story.StoryAddRequest
import com.raihan.story.data.model.dto.story.StoryAddResponse
import com.raihan.story.data.model.dto.story.StoryAllResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST

interface StoryService {
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("stories")
    suspend fun getAll(): StoryAllResponse

    @Multipart
    @POST("stories")
    suspend fun upload(
        @Body storyAddRequest: StoryAddRequest
    ): StoryAddResponse
}