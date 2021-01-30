package com.kangaroo.filmore.Views.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kangaroo.filmore.R

class SearchFragment : Fragment() {

    val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val viewPager2 = root.findViewById<ViewPager2>(R.id.view_pager_search_activity)

        val adapter = com.kangaroo.filmore.Utils.PagerAdapter(
            requireActivity().supportFragmentManager,
            requireActivity().lifecycle
        )
        viewPager2.adapter = adapter
        val tabLayout = root.findViewById<TabLayout>(R.id.tab_layout_search)

        TabLayoutMediator(
            tabLayout, viewPager2
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Movies"
                1 -> tab.text = "TV"
            }
        }.attach()

        val searchView: SearchView = root.findViewById(R.id.search_view_in_fragment)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()){
                    searchViewModel.liveQuery.postValue(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        return root
    }
}