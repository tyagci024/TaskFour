package com.example.taskfour.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskfour.databinding.NewsItemLayoutBinding
import com.example.taskfour.model.NewsItem
import com.example.taskfour.utilies.downloadFromURL

class NewsAdapter(private val newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = newsList[position]

        holder.bind(newsItem)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(val binding: NewsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsItem) {
            binding.apply {
                textViewTitle.text = news.title
                textViewSource.text=news.source
                imageViewThumbnail.downloadFromURL(news.imgUrl.toString())
                root.setOnClickListener {
                    onItemClickListener?.invoke(news.sourceLink)
                }
            }
        }
    }
}
