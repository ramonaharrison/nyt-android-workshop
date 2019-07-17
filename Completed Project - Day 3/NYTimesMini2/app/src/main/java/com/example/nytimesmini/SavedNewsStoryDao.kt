package com.example.nytimesmini

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedNewsStoryDao {
    @Query("SELECT * FROM saved_news_stories")
    suspend fun getAll(): List<SavedNewsStory>

    @Query("SELECT * FROM saved_news_stories WHERE click_url LIKE :clickUrl")
    suspend fun get(clickUrl: String): List<SavedNewsStory>

    @Insert
    suspend fun insert(story: SavedNewsStory)

    @Delete
    suspend fun delete(story: SavedNewsStory)
}