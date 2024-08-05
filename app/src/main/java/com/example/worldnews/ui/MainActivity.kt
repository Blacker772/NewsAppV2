package com.example.worldnews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.worldnews.R
import com.example.worldnews.ui.news.ViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}