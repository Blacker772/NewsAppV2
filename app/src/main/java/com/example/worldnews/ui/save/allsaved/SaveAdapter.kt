package com.example.worldnews.ui.save.allsaved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.worldnews.data.database.DatabaseModel
import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.databinding.SaveItemBinding
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class SaveAdapter:ListAdapter<DatabaseModel, SaveAdapter.SaveViewHolder>(DIFF_UTIL) {

    private var onItemClick: ((news: DatabaseModel) -> Unit)? = null
    fun onItemClickListener(onItemClick: (news: DatabaseModel) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SaveItemBinding.inflate(inflater, parent, false)
        return SaveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaveViewHolder, position: Int) {
       holder.bind(currentList[position], onItemClick)
    }

    inner class SaveViewHolder(private var binding: SaveItemBinding): ViewHolder(binding.root){
        fun bind(data: DatabaseModel, onItemClick: ((news: DatabaseModel) -> Unit)?){
            binding.image.load(data.imageUrl)
            binding.tvTitle.text = data.title
            binding.tvPublishedDate.text = convertDate(data.pubDate)
            binding.tvNewsDetails.text = data.content
            binding.tvReference.text = data.sourceId
            binding.cardItemLayout.setOnClickListener {
                onItemClick?.invoke(data)
            }
        }
    }

    private fun convertDate(data: String?): String {
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
        val DIFF_UTIL = object : DiffUtil.ItemCallback<DatabaseModel>(){
            override fun areItemsTheSame(
                oldItem: DatabaseModel,
                newItem: DatabaseModel
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: DatabaseModel,
                newItem: DatabaseModel
            ): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.content == newItem.content &&
                        oldItem.link == newItem.link &&
                        oldItem.pubDate == newItem.pubDate &&
                        oldItem.imageUrl == newItem.imageUrl &&
                        oldItem.sourceId == newItem.sourceId
            }
        }
    }
}