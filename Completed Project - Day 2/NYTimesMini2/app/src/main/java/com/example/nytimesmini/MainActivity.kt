package com.example.nytimesmini

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter : NewsAdapter

    val news = listOf(
        NewsStory(
            headline = "Is It Time to Play With Spaceships Again?",
            summary = "After 50 years of Apollo nostalgia, we have yet to fully answer the central question: Why send humans into space?",
            imageUrl = "https://static01.nyt.com/images/2019/07/14/science/14MOONOVERBYE/3cc30ecf583c4e8daaad79c79dd287c5-jumbo.jpg",
            clickUrl = "https://www.nytimes.com/2019/07/15/science/apollo-moon-space.html"),
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NewsAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.updateNews(news)
    }
}
