package com.example.nytimesmini

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_news_stories")
data class SavedNewsStory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "headline") val headline: String,
    @ColumnInfo(name = "summary") val summary: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "click_url") val clickUrl: String
) {
    fun NewsStory.toSavedVersion() : SavedNewsStory {
        return SavedNewsStory(headline = this.headline,
            summary = this)
    }
}