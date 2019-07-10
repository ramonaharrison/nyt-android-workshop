package com.ramonaharrison.mininytimes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: NewsAdapter

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
        val service = ApiFactory.topStoriesApi
        GlobalScope.launch(Dispatchers.Main) {
            val postRequest = service.getSection("science", BuildConfig.API_KEY)
            try {
                val response = postRequest.await()
                response.body()?.let { section ->
                    val news = section.results.map { result ->
                        NewsStory(headline = result.title,
                            summary = result.abstract,
                            imageUrl = result.multimedia.firstOrNull { it.format == "superJumbo" }?.url,
                            clickUrl = result.url)
                    }
                    adapter.updateNews(news)
                }
            } catch (e: Exception) {
                Toast.makeText(baseContext, "Something went wrong.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
