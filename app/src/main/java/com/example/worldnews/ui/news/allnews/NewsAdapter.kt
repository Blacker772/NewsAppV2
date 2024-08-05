package com.example.worldnews.ui.news.allnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldnews.data.emilnews.NewsItem
import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.databinding.NewsItemBinding
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class NewsAdapter : ListAdapter<Articles, NewsAdapter.NewsViewHolder>(DIFF_UTIL) {

    private var onItemClick: ((news: Articles) -> Unit)? = null
    fun onItemClickListener(onItemClick: (news: Articles) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(currentList[position], onItemClick)
    }

    inner class NewsViewHolder(private val binding: NewsItemBinding) : ViewHolder(binding.root) {
        fun bind(data: Articles, onItemClick: ((news: Articles) -> Unit)?) {
            binding.sivImage.load(data.image)
            binding.tvTitle.text = data.title
            binding.tvPublishedDate.text = convertDate(data.publishedAt)
            binding.tvNewsDetails.text = data.description
            binding.tvReference.text = data.source.name.toString()
            binding.cardItemLayout.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    private fun convertDate(data: String): String {
        try {
            val zoneDateTime = ZonedDateTime.parse(data)
            val localZonedDateTime = zoneDateTime.withZoneSameInstant(ZoneId.of("Asia/Baku"))
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM, HH:mm").withLocale(Locale("ru_RU")))
            return date

        } catch (e: Exception) {
            val zoneDateTime = ZonedDateTime.parse(data)
            val localZonedDateTime = zoneDateTime.withZoneSameInstant(ZoneId.of("Asia/Baku"))
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM, HH:mm"))
            return date
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(
                oldItem: Articles,
                newItem: Articles
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Articles,
                newItem: Articles
            ): Boolean {
                return oldItem.description == newItem.description &&
                        oldItem.title == newItem.title &&
                        oldItem.content == newItem.content
            }
        }
    }

}
