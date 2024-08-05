package com.example.worldnews.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2
import com.example.worldnews.R
import com.example.worldnews.databinding.FragmentViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Создание адаптера
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        //привязка адаптера к viewPager2
        binding.viewPager2.adapter = adapter

        //синхронизация меню с перелистыванием фрагментов
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigation.menu.children.forEachIndexed { index, _ ->
                    binding.bottomNavigation.menu.getItem(position).setChecked(index == position)
                }
            }
        })

        //нижнее меню приложения
        binding.bottomNavigation.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.newsFragment -> {
                    changePosition(0)
                    return@setOnItemSelectedListener true
                }
                R.id.saveFragment ->{
                    changePosition(1)
                    return@setOnItemSelectedListener true
                }
                R.id.settingFragment -> {
                    changePosition(2)
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }

    //перелистывание фрагментов
    private fun changePosition(position: Int) {
        binding.viewPager2.setCurrentItem(position,true)
    }

}