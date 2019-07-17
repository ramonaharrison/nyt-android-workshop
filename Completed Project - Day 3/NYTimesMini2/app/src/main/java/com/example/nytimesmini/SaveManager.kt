package com.example.nytimesmini

import android.content.Context
import androidx.room.Room

object SaveManager {

    lateinit var database: SavedNewsStoryDatabase

    fun initDatabase(applicationContext: Context) {
        database = Room.databaseBuilder(
            applicationContext,
            SavedNewsStoryDatabase::class.java, "saved-news-story-db"
        ).build()
    }

    suspend fun save(newsStory: NewsStory) {
        database.storyDao().insert(newsStory.toSavedNewsStory())
    }

    suspend fun unsave(newsStory: NewsStory) {
        database.storyDao().delete(newsStory.toSavedNewsStory())
    }

    suspend fun isSaved(newsStory: NewsStory): Boolean {
        return database.storyDao().get(newsStory.clickUrl).isNotEmpty()
    }

    suspend fun getSavedStories(): List<NewsStory> {
        return database.storyDao().getAll().map { savedNewsStory -> savedNewsStory.toNewsStory() }
    }

    fun NewsStory.toSavedNewsStory() : SavedNewsStory {
        return SavedNewsStory(
            headline = this.headline,
            summary = this.summary,
            imageUrl = this.imageUrl,
            clickUrl = this.clickUrl)
    }

    fun SavedNewsStory.toNewsStory() : NewsStory {
        return NewsStory(
            headline = this.headline,
            summary = this.summary,
            imageUrl = this.imageUrl,
            clickUrl = this.clickUrl)
    }
}