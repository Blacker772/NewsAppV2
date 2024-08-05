package com.example.worldnews.ui.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.worldnews.ui.news.allnews.NewsFragment
import com.example.worldnews.ui.save.allsaved.SaveFragment
import com.example.worldnews.ui.search.allsearch.SearchFragment

//адаптер для пейджэра чтобы можно было листать
class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsFragment()
            1 -> SaveFragment()
            else -> SearchFragment()
        }
    }
    override fun getItemCount(): Int{
        return 3
    }
}