package com.tranphunguyen.newsreader

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(var listNews: ArrayList<News>): RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_news,p0,false)

        val newsHolder = NewsHolder(view)

        view.setOnClickListener {
            val intent = Intent(p0.context as MainActivity,Web::class.java)
            intent.putExtra("url",listNews[newsHolder.layoutPosition].url)

            p0.context.startActivity(intent)
        }


     return newsHolder
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(p0: NewsHolder, p1: Int) {
      p0.itemView.titleNew.text = listNews[p1].title
    }

    fun setList(list: ArrayList<News>) {
        listNews = list
        notifyDataSetChanged()
    }
    class NewsHolder(view: View): RecyclerView.ViewHolder(view)
}