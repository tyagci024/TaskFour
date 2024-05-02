package com.example.taskfour

import com.example.taskfour.adapter.NewsAdapter
import com.example.taskfour.model.NewsItem

interface ClickInterface {
    fun onWebsiteClicked(newsItem: NewsItem,holder: NewsAdapter.ViewHolder)
}