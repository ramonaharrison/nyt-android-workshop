package com.ramonaharrison.mininytimes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = NewsAdapter(this)


        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        updateNews()
    }

    fun updateNews() {
        val news = listOf(
            NewsStory(
                headline = "Crocodiles Went Through a Vegetarian Phase, Too",
                summary = "Ancestors of modern crocodiles evolved to survive on a plant diet at least three times, researchers say.",
                imageUrl = "https://static01.nyt.com/images/2019/07/09/science/27TB-VEGGIECROC1/27TB-VEGGIECROC1-videoLarge.jpg",
                clickUrl = "https://www.nytimes.com/2019/06/27/science/crocodiles-vegetarian-teeth.html"),

            NewsStory(
                headline = "This Cockatoo Thinks He Can Dance",
                summary = "Researchers have become convinced that Snowball, a YouTube sensation, and perhaps other animals, share humans’ sensitivity to music.",
                imageUrl = "https://static01.nyt.com/images/2019/07/09/autossell/Screen-Shot-2019-07-09-at-1/Screen-Shot-2019-07-09-at-1-videoLarge.jpg",
                clickUrl = "https://www.nytimes.com/2019/07/09/science/cockatoo-snowball-dance.html"),

            NewsStory(
                headline = "The Moon Is a Hazardous Place to Live",
                summary = "If we get back to the lunar surface, astronauts will have to contend with much more than perilous rocket flights and the vacuum of space.",
                imageUrl = "https://static01.nyt.com/images/2019/07/14/science/14MOONHAZARDS4/14MOONHAZARDS4-mediumThreeByTwo440-v2.jpg",
                clickUrl = "https://www.nytimes.com/2019/07/08/science/apollo-moon-colony-dangers.html")
        )
        adapter.updateNews(news)
    }
}
