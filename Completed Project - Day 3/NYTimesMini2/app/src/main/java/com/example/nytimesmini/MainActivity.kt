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
        "Travel" to "travel",
        "Saved" to "saved"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SaveManager.initDatabase(applicationContext)

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
        if (path == "saved") {
            GlobalScope.launch(Dispatchers.Main) {
                val news = SaveManager.getSavedStories()
                adapter.updateNews(news)
            }
        } else {
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
}
