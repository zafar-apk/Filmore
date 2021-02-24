package com.kangaroo.filmore.Views.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.Constants
import com.kangaroo.filmore.Utils.HomeAdapterForRecyclerView


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerViewMovie = root.findViewById<RecyclerView>(R.id.recycler_home_movies)
        val layoutManagerMovie = LinearLayoutManager(context)
        layoutManagerMovie.orientation = HORIZONTAL

        recyclerViewMovie.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManagerMovie
            adapter = HomeAdapterForRecyclerView(requireContext(), Constants.MEDIA_TYPE_MOVIE)
        }

        val recyclerViewTV = root.findViewById<RecyclerView>(R.id.recycler_home_tv_shows)
        val layoutManagerTV = LinearLayoutManager(context)
        layoutManagerTV.orientation = HORIZONTAL

        recyclerViewTV.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManagerTV
            adapter = HomeAdapterForRecyclerView(requireContext(), Constants.MEDIA_TYPE_TV)
        }


        return root
    }
}