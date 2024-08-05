package com.example.worldnews.ui.news.allnews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.worldnews.R
import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.databinding.FragmentNewsBinding
import com.example.worldnews.ui.UiState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel by viewModels<NewsViewModel>()
    private val adapter = NewsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getNews()
            viewModel.state.collect{
                onChangeState(it)
            }
        }

        //Обработка выбора категории
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {

            override fun onTabSelected(p0: TabLayout.Tab?) {
                lifecycleScope.launch {
                    viewModel.getNewsByCategory(p0?.text.toString())
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {
                lifecycleScope.launch {
                    viewModel.getNewsByCategory(p0?.text.toString())
                }
            }
        })

        //Инициализация адаптера
        binding.newsList.adapter = adapter

        //Обработка клика по элементу списка
        adapter.onItemClickListener {
            val news = Articles(
                it.title, it.description, it.content, it.url, it.image, it.publishedAt, it.source
            )
            val bundle = Bundle()
            bundle.putParcelable("news", news)
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_newsDetailsFragment,
                bundle
            )
        }

        //Подписка на изменение состояния
        lifecycleScope.launch {
            viewModel.state.collect {
                onChangeState(it)
            }
        }

        //Добаление категорий в tabLayout
        listCategory.forEach {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it))
        }

        //Обновление новостей
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true

            //Получение выбранной категории
            val selectedTabPosition = binding.tabLayout.selectedTabPosition

            //Получение названия выбранной категории
            val selectedCategory = binding.tabLayout.getTabAt(selectedTabPosition)?.text.toString()

            lifecycleScope.launch {
                //Получение новостей по выбранной категории
                viewModel.getNewsByCategory(selectedCategory)
                viewModel.state.collect {
                    onChangeState(it)
                }
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    //Метод, обрабатывающий состояния UiState
    private fun onChangeState(state: UiState) {
        when (state) {
            is UiState.Loading -> {
                binding.progressBar.isVisible = state.isLoading
            }
            is UiState.Error -> {
                Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
            }
            is UiState.Data -> {
                adapter.submitList(state.data)
                binding.progressBar.isVisible = state.isLoading
            }
            else -> {}
        }
    }

    //Список категорий
    private val listCategory = listOf(
        "general",
        "world",
        "nation",
        "business",
        "technology",
        "entertainment",
        "sports",
        "science",
        "health"
    )
}

