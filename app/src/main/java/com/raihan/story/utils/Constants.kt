package com.raihan.story.utils

import com.raihan.story.R
import com.raihan.story.data.di.networkModule
import com.raihan.story.data.di.repositoryModule
import com.raihan.story.data.di.viewModelModule

val koinModules = listOf(
    networkModule,
    repositoryModule,
    viewModelModule,
)

val bottomBarScope = listOf(R.id.listStoryFragment, R.id.settingsFragment)

const val BASE_URL = "https://story-api.dicoding.dev/v1/"

const val PREFS_NAME: String = "auth_pref"
const val TOKEN_KEY: String = "auth_token"
const val NAME_KEY: String = "auth_name"
