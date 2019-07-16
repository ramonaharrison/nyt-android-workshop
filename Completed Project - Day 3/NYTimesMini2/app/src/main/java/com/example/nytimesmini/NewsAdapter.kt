package com.example.nytimesmini

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
        holder.itemView.headline.text = news[position].headline
        holder.itemView.summary.text = news[position].summary

        Picasso.get().load(news[position].imageUrl).into(holder.itemView.imageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ArticleActivity::class.java)
            intent.putExtra("url", news[position].clickUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

}