package com.example.githubapp.presentation.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapp.presentation.home.fragments.FavoritesFragment
import com.example.githubapp.presentation.home.fragments.RepositoryFragment
import com.example.githubapp.util.Constants.NUM_TABS

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RepositoryFragment()
            else -> FavoritesFragment()
        }
    }
}
