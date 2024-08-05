package com.example.worldnews.ui.search.allsearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldnews.R
import com.example.worldnews.data.responsemodel.Articles
import com.example.worldnews.databinding.FragmentSearchBinding
import com.example.worldnews.ui.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val adapter = SearchAdapter()
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()

        lifecycleScope.launch {
            viewModel.getNews()
            viewModel.state.collect {
                onChangeState(it)
            }
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    if (newText == null) {
                        viewModel.getNews()
                        viewModel.state.collect {
                            onChangeState(it)
                        }
                    } else {
                        newText.let { search ->
                            viewModel.getSearchNews(search)
                            viewModel.state.collect {
                                onChangeState(it)
                            }
                        }
                    }
                }
                return true
            }
        })
    }

    //Метод, обрабатывающий состояния UiState
    private fun onChangeState(state: UiState) {
        when (state) {
            is UiState.Data -> {
                adapter.submitList(state.data)
                binding.progressBar.isVisible = state.isLoading
            }

            is UiState.Loading -> {
                binding.progressBar.isVisible = state.isLoading
            }

            is UiState.Error -> {
                Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }

            is UiState.None -> {
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun initRV() {
        binding.rvSearchList.adapter = adapter
        binding.rvSearchList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter.onItemClickListener {
            val news = Articles(
                it.title, it.description, it.content, it.url, it.image, it.publishedAt, it.source
            )
            val bundle = Bundle()
            bundle.putParcelable("news", news)
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_searchDetailsFragment,
                bundle
            )
        }
    }
}