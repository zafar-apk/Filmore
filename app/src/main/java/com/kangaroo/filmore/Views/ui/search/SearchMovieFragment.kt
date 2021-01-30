package com.kangaroo.filmore.Views.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.MovieAdapter
import com.kangaroo.filmore.Utils.OnItemClickListener
import com.kangaroo.filmore.Views.BottomMainActivity


class SearchMovieFragment : Fragment(), OnItemClickListener {

    val searchViewModel: SearchViewModel by activityViewModels()

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAndTvAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? { val root = inflater.inflate(R.layout.fragment_search_movie, container, false)
        recyclerView = root.findViewById(R.id.recycler_view_movie_search)

        setRecyclerView()

        searchViewModel.liveQuery.observe(viewLifecycleOwner, {
            searchViewModel.findMovies(it)
        })

        searchViewModel.foundData.observe(viewLifecycleOwner, {
            recyclerViewAndTvAdapter.submitList(it)
        })

        return root
    }

    private fun setRecyclerView() {
        recyclerViewAndTvAdapter = MovieAdapter(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerViewAndTvAdapter
    }



    override fun onItemClick(oneMovie: OneMovie) {
        val activity = activity as BottomMainActivity
        activity.onClickItem(oneMovie)
    }
}

