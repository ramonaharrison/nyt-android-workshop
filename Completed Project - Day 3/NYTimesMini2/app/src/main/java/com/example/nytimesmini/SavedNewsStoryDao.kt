package com.example.nytimesmini

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedNewsStoryDao {
    @Query("SELECT * FROM saved_news_stories")
    fun getAll(): List<SavedNewsStory>

    @Query("SELECT * FROM saved_news_stories WHERE click_url LIKE :clickUrl")
    fun get(clickUrl: String): List<SavedNewsStory>

    @Insert
    fun insert(story: SavedNewsStory)

    @Delete
    fun delete(story: SavedNewsStory)
}