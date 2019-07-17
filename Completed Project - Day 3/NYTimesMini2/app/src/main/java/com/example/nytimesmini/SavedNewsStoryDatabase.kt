package com.example.nytimesmini

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SavedNewsStory::class), version = 1)
abstract class SavedNewsStoryDatabase : RoomDatabase() {
    abstract fun storyDao(): SavedNewsStoryDao
}