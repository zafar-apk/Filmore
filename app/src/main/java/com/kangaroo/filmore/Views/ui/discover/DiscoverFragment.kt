package com.kangaroo.filmore.Views.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.OnItemClickListener
import com.kangaroo.filmore.Utils.PagedMovieAdapter
import com.kangaroo.filmore.Views.BottomMainActivity

class DiscoverFragment : Fragment(), OnItemClickListener {

    private val discoverViewModel: DiscoverViewModel by viewModels()
    private lateinit var moviesAdapter: PagedMovieAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_discover, container, false)
        setRecyclerView(root)

        val skeletonScreen = Skeleton.bind(recyclerView)
            .adapter(moviesAdapter)
            .load(R.layout.skeleton_movie_discover)
            .show()

        discoverViewModel.livePagedList.observe(viewLifecycleOwner,{
            moviesAdapter.submitList(it)
            skeletonScreen.hide()
        })

//        discoverViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, {
//            if (BottomMainActivity.hasConnection(requireContext())){
//                skeletonScreen.hide()
//                moviesAdapter.submitList(it)
//            }
//        })

        return root
    }

    private fun setRecyclerView(view: View) {
        moviesAdapter = PagedMovieAdapter(this)
        recyclerView = view.findViewById(R.id.discover_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = moviesAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }


    override fun onItemClick(oneMovie: OneMovie) {
        val activity = activity as BottomMainActivity
        activity.onClickItem(oneMovie)
    }


}