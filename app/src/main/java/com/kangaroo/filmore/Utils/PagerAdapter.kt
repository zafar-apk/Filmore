package com.kangaroo.filmore.Utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kangaroo.filmore.Views.ui.search.SearchMovieFragment
import com.kangaroo.filmore.Views.ui.search.SearchTVFragment


class PagerAdapter(fm: FragmentManager, behavior: Lifecycle)
    : FragmentStateAdapter(fm, behavior) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> (SearchMovieFragment.newInstance())
            1 -> (SearchTVFragment.newInstance())
            else -> (SearchMovieFragment.newInstance())
        }
    }

}