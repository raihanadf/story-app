package com.raihan.story.data.model.data

data class User(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
