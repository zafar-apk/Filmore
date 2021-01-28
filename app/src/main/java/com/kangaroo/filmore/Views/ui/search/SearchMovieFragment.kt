package com.kangaroo.filmore.Views.ui.search


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.Constants.LOG_TAG
import com.kangaroo.filmore.Utils.MovieAdapter
import com.kangaroo.filmore.Utils.OnItemClickListener
import com.kangaroo.filmore.Views.BottomMainActivity

class SearchMovieFragment : Fragment(), OnItemClickListener {

    private val searchViewModel: SearchViewModel by activityViewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search_movie, container, false)

        recyclerView = root.findViewById(R.id.recycler_view_movie_search)

        recyclerViewAdapter = MovieAdapter(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerViewAdapter

        searchViewModel.query.observe(viewLifecycleOwner, {
            searchViewModel.searchMoviesAndTV(it)
        })

        searchViewModel.liveListOfMovies?.observe(viewLifecycleOwner, {
            Log.d(LOG_TAG, "Data is observing -*-*-*-*-*--*-*-")
            recyclerViewAdapter.submitList(it)
        })

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SearchMovieFragment()
    }

    override fun onItemClick(id: String, oneMovie: OneMovie) {
        val activity = activity as BottomMainActivity
        activity.onClickItem(id, oneMovie)
    }
}

