package com.example.worldnews.ui.save.detailsnews

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.worldnews.R
import com.example.worldnews.data.database.DatabaseModel
import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.databinding.FragmentSaveDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSaveDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSaveDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Получение данных из аргументов
        val news = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("news", DatabaseModel::class.java)
        } else {
            arguments?.getParcelable("news")
        }

        //Заполнение данных
        binding.tvTitle.text = news?.title.toString()
        binding.tvContent.text = news?.content.toString()
        binding.ivNews.load(news?.imageUrl)

        binding.ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        //Поделиться новостью
        binding.ibShare.setOnClickListener {
            val intentShare = Intent(Intent.ACTION_SEND)
            intentShare.type = "text/plain"
            intentShare.putExtra(Intent.EXTRA_TEXT, news?.link)
            startActivity(Intent.createChooser(intentShare, "News..."))
        }
    }
}