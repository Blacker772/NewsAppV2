package com.example.worldnews.ui.news.detailsnews

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.worldnews.data.database.DatabaseModel
import com.example.worldnews.R
import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.databinding.FragmentNewsDetailsBinding
import com.example.worldnews.ui.MainActivity
import com.example.worldnews.ui.news.allnews.NewsFragment
import com.example.worldnews.ui.save.allsaved.SaveViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val viewModel: SaveViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNewsDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Получение данных из аргументов
        val news = if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("news", Articles::class.java)
        } else {
            arguments?.getParcelable("news")
        }

        //Заполнение данных
        binding.tvTitle.text = news?.title.toString()
        binding.tvContent.text = news?.content.toString()
        binding.ivNews.load(news?.image)

        //Проверка сохранена ли новость
        CoroutineScope(Dispatchers.IO).launch {
            val linkOfData = viewModel.getLinkData(news?.url.toString())
            if (linkOfData == news?.url) {
                binding.ibSave.setImageResource(R.drawable.save_disabled)
                binding.ibSave.isEnabled = false
            }else{
                binding.ibSave.setImageResource(R.drawable.save_enabled)
                binding.ibSave.isEnabled = true
            }
        }

        //Кнопка назад
        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //Кнопка поделиться
        binding.ibShare.setOnClickListener {
            val intentShare = Intent(Intent.ACTION_SEND)
            intentShare.type = "text/plain"
            intentShare.putExtra(Intent.EXTRA_TEXT, news?.url)
            startActivity(Intent.createChooser(intentShare, "News..."))
        }

        //Кнопка сохранить
        binding.ibSave.setOnClickListener {
            lifecycleScope.launch {
                viewModel.insert(DatabaseModel(
                    null, news?.title, news?.url, news?.content, news?.publishedAt, news?.image, news?.source?.url
                ))
                binding.ibSave.setImageResource(R.drawable.save_disabled)
            }
            Toast.makeText(requireContext(), "Added in save", Toast.LENGTH_SHORT).show()
        }
    }
}