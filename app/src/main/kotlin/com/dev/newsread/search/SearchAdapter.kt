package com.dev.newsread.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dev.newsread.R
import com.dev.newsread.articles.ArticlesViewHolder
import com.dev.newsread.data.Article

class SearchAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArticlesViewHolder>() {

	init {
		setHasStableIds(false)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(R.layout.item_article, parent, false)
		return ArticlesViewHolder(view)
	}

	override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
		holder.bind(articles[position])
	}

	override fun getItemCount(): Int = articles.size
}