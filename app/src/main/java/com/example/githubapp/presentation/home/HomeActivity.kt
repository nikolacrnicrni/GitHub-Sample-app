package com.example.githubapp.presentation.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.githubapp.R
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.presentation.home.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val tabArray = arrayOf(
        "Repositories",
        "Favorites"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_stars -> {
                viewModel.sortingState.postValue(SortingRepos.STARS)
                return true
            }
            R.id.sort_by_forks -> {
                viewModel.sortingState.postValue(SortingRepos.FORKS)
                return true
            }
            R.id.sort_by_updated -> {
                viewModel.sortingState.postValue(SortingRepos.UPDATED)
                return true
            }
            R.id.reset -> {
                viewModel.sortingState.postValue(SortingRepos.RESET)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
