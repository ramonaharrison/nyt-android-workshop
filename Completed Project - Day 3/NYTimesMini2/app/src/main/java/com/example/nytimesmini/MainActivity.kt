package com.example.nytimesmini

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter

    val sections = mapOf(
        "Home" to "home",
        "Opinion" to "opinion",
        "Food" to "food",
        "Science" to "science",
        "Travel" to "travel"
    )

    val news = listOf(
        NewsStory(
            headline = "Is It Time to Play With Spaceships Again?",
            summary = "After 50 years of Apollo nostalgia, we have yet to fully answer the central question: Why send humans into space?",
            imageUrl = "https://static01.nyt.com/images/2019/07/14/science/14MOONOVERBYE/3cc30ecf583c4e8daaad79c79dd287c5-jumbo.jpg",
            clickUrl = "https://www.nytimes.com/2019/07/15/science/apollo-moon-space.html"
        ),
        NewsStory(
            headline = "Crocodiles Went Through a Vegetarian Phase, Too",
            summary = "Ancestors of modern crocodiles evolved to survive on a plant diet at least three times, researchers say.",
            imageUrl = "https://static01.nyt.com/images/2019/07/09/science/27TB-VEGGIECROC1/27TB-VEGGIECROC1-videoLarge.jpg",
            clickUrl = "https://www.nytimes.com/2019/06/27/science/crocodiles-vegetarian-teeth.html"
        ),

        NewsStory(
            headline = "This Cockatoo Thinks He Can Dance",
            summary = "Researchers have become convinced that Snowball, a YouTube sensation, and perhaps other animals, share humans’ sensitivity to music.",
            imageUrl = "https://static01.nyt.com/images/2019/07/09/autossell/Screen-Shot-2019-07-09-at-1/Screen-Shot-2019-07-09-at-1-videoLarge.jpg",
            clickUrl = "https://www.nytimes.com/2019/07/09/science/cockatoo-snowball-dance.html"
        ),
        NewsStory(
            headline = "The Moon Is a Hazardous Place to Live",
            summary = "If we get back to the lunar surface, astronauts will have to contend with much more than perilous rocket flights and the vacuum of space.",
            imageUrl = "https://static01.nyt.com/images/2019/07/14/science/14MOONHAZARDS4/14MOONHAZARDS4-mediumThreeByTwo440-v2.jpg",
            clickUrl = "https://www.nytimes.com/2019/07/08/science/apollo-moon-colony-dangers.html"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NewsAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        setupSections()

        val selected = tabLayout.getTabAt(tabLayout.selectedTabPosition)!!.text
        updateNews(sections[selected]!!)
    }

    fun setupSections() {
        for (key in sections.keys) {
            tabLayout.addTab(tabLayout.newTab().setText(key))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateNews(sections[tab.text]!!)
                recyclerView.scrollToPosition(0)
            }

            override fun onTabReselected(p0: TabLayout.Tab) {
                // Do nothing.
            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
                // Do nothing.
            }

        })
    }

    fun updateNews(path: String) {
        val service = ApiFactory.topStoriesApi
        GlobalScope.launch(Dispatchers.Main) {
            val postRequest = service.getSection(path, BuildConfig.API_KEY)
            try {
                val response = postRequest.await()
                response.body()?.let { section ->
                    val news = section.results.map { result ->
                        NewsStory(
                            headline = result.title,
                            summary = result.abstract,
                            imageUrl = result.multimedia.firstOrNull { it.format == "superJumbo" }?.url,
                            clickUrl = result.url
                        )
                    }
                    adapter.updateNews(news)
                }

            } catch (e: Exception) {
                Toast.makeText(baseContext, "Something went wrong.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
