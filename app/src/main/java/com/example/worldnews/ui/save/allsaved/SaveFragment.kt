package com.example.worldnews.ui.save.allsaved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldnews.R
import com.example.worldnews.data.database.DatabaseModel
import com.example.worldnews.databinding.FragmentSaveBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SaveFragment : Fragment() {

    private lateinit var binding: FragmentSaveBinding
    private val adapter = SaveAdapter()
    private val viewModel: SaveViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSaveBinding.inflate(layoutInflater, container, false)

        //Инициализация адаптера
        binding.saveList.adapter = adapter

        //Инициализация LayoutManager
        binding.saveList.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        adapter.onItemClickListener {
            val news = DatabaseModel(
                null, it.title, it.link, it.content, it.pubDate, it.imageUrl, it.sourceId
            )
            val bundle = Bundle()
            bundle.putParcelable("news", news)
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_saveDetailsFragment,
                bundle
            )
        }

        //Подписка на данные
        viewModel.state.observe(viewLifecycleOwner) {
            it.let {
                adapter.submitList(it)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Кнопка удаления
        binding.ibDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.delete()
                adapter.submitList(emptyList())
            }
            Toast.makeText(requireContext(), "Data cleared!", Toast.LENGTH_SHORT).show()
        }
    }
}


