package com.example.nytimesmini

import android.content.Context
import androidx.room.Room

object SaveManager {

    private val savedStories = ArrayList<NewsStory>()

    lateinit var database: SavedNewsStoryDatabase

    fun initDatabase(applicationContext: Context) {
        database = Room.databaseBuilder(
            applicationContext,
            SavedNewsStoryDatabase::class.java, "saved-news-story-db"
        ).build()
    }

    fun save(newsStory: NewsStory) {
        database.storyDao().insert(newsStory.saved)
    }

    fun unsave(newsStory: NewsStory) {
        savedStories.remove(newsStory)
    }

    fun isSaved(newsStory: NewsStory): Boolean {
        return savedStories.contains(newsStory)
    }

    fun getSavedStories(): List<NewsStory> {
        return savedStories.toList()
    }
}