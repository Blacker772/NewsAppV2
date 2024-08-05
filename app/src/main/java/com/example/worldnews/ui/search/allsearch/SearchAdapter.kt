package com.example.worldnews.ui.search.allsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.databinding.SearchItemBinding
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class SearchAdapter: ListAdapter<Articles, SearchAdapter.SearchViewHolder>(DIFF_UTIL){

    private var onItemClick:((note: Articles) -> Unit)? = null
    fun onItemClickListener(onItemClick: (note: Articles) -> Unit){
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchItemBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(currentList[position], onItemClick)
    }

    inner class SearchViewHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Articles, onItemClick:((note: Articles) -> Unit)?){
            binding.sivImage.load(data.image)
            binding.tvTitle.text = data.title
            binding.tvNewsDetails.text = data.description
            binding.tvPublishedDate.text = convertDate(data.publishedAt)
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
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM, HH:mm").withLocale(
                Locale("ru_RU")
            ))
            return date

        } catch (e: Exception) {
            val zoneDateTime = ZonedDateTime.parse(data)
            val localZonedDateTime = zoneDateTime.withZoneSameInstant(ZoneId.of("Asia/Baku"))
            val date = localZonedDateTime.format(DateTimeFormatter.ofPattern("dd MMMM, HH:mm"))
            return date
        }
    }

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Articles>(){
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