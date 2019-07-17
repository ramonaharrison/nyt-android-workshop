package com.example.nytimesmini

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    var news: List<NewsStory> = ArrayList()

    fun updateNews(news: List<NewsStory>){
        this.news = news
        notifyDataSetChanged()
    }

    class NewsViewHolder(val view : View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = layoutInflater.inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)

    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsStory = news[position]

        holder.itemView.headline.text = newsStory.headline
        holder.itemView.summary.text = newsStory.summary

        Picasso.get().load(newsStory.imageUrl).into(holder.itemView.imageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ArticleActivity::class.java)
            intent.putExtra("url", newsStory.clickUrl)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.shareButton.setOnClickListener {
            val url = newsStory.clickUrl
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out this NYTimes article: $url")
                type = "text/plain"
            }
            holder.itemView.context.startActivity(Intent.createChooser(sendIntent, "Share this article to:"))
        }

        holder.itemView.saveButton.setOnClickListener {
            if (SaveManager.isSaved(newsStory)) {
                SaveManager.unsave(newsStory)
                holder.itemView.saveButton.setColorFilter(Color.BLACK)
            } else {
                SaveManager.save(newsStory)
                holder.itemView.saveButton.setColorFilter(Color.MAGENTA)
            }
        }

        if (SaveManager.isSaved(newsStory)) {
            holder.itemView.saveButton.setColorFilter(Color.MAGENTA)
        } else {
            holder.itemView.saveButton.setColorFilter(Color.BLACK)
        }
    }

}