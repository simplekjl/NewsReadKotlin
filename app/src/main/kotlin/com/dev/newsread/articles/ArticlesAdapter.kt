package com.dev.newsread.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dev.newsread.R
import com.dev.newsread.data.Article
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

/**
 * Adapter for the articles
 * Created by jlcs on 1/27/18.
 */

class ArticlesAdapter(var articles: OrderedRealmCollection<Article>) : RealmRecyclerViewAdapter<Article, ArticlesViewHolder>(articles, true) {

    init {
        setHasStableIds(false)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_article, parent, false)
        return ArticlesViewHolder(view)
    }

    fun onDataChanged(results:OrderedRealmCollection<Article>) {
        articles = results
        updateData(results)
    }
}