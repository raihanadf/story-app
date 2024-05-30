package com.raihan.story.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raihan.story.data.local.dao.StoryDao
import com.raihan.story.data.model.dto.story.Story

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}