package com.raihan.story.data.model.dto.auth

import retrofit2.http.Field

data class RegisterRequest(
    @Field("name")
    val name: String,

    @Field("email")
    val email: String,

    @Field("password")
    val password: String
)