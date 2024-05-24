package com.raihan.story.data.di

import com.raihan.story.data.repository.story.StoryRepository
import com.raihan.story.data.repository.story.StoryRepositoryImpl
import com.raihan.story.data.repository.user.AuthRepository
import com.raihan.story.data.repository.user.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<StoryRepository> { StoryRepositoryImpl(get()) }
}