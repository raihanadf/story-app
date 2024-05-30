package com.raihan.story.data.di

import androidx.room.Room
import com.raihan.story.data.local.StoryDatabase
import com.raihan.story.utils.BASE_DATABASE
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            StoryDatabase::class.java,
            BASE_DATABASE
        ).build()
    }

    single {
        get<StoryDatabase>().storyDao()
        get<StoryDatabase>().remoteKeysDao()
    }
}
