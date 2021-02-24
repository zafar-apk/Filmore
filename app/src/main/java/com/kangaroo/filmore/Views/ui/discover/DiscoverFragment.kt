package com.kangaroo.filmore.Views.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.Constants.LOG_TAG
import com.kangaroo.filmore.Utils.OnItemClickListener
import com.kangaroo.filmore.Utils.PagedMovieAdapter
import com.kangaroo.filmore.Views.BottomMainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiscoverFragment : Fragment(), OnItemClickListener {

    private val discoverViewModel: DiscoverViewModel by viewModels()
    private lateinit var moviesAdapter: PagedMovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab_scrollUp: FloatingActionButton
    lateinit var liveDataChangeListener: MutableLiveData<OneMovie>
    lateinit var skeletonScreen: RecyclerViewSkeletonScreen

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_discover, container, false)

        liveDataChangeListener = MutableLiveData()

        setRecyclerView(root)

        setupView()

        skeletonScreen = Skeleton.bind(recyclerView)
            .adapter(moviesAdapter)
            .load(R.layout.skeleton_movie_discover)
            .show()

        return root
    }


    private fun setRecyclerView(view: View) {
        moviesAdapter = PagedMovieAdapter(this)
        fab_scrollUp = view.findViewById(R.id.fab_scroll_up)

        recyclerView = view.findViewById(R.id.discover_recycler)
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = moviesAdapter
            this.layoutManager = GridLayoutManager(requireContext(), 2)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    var previousDy = 0
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy < 0) {
                        previousDy = dy
                        showFabScrollUp()
                    }
                    if (dy > previousDy) {
                        hideFabScrollUp()
                    }
                }
            })

        }
        
        moviesAdapter.addLoadStateListener { loadState ->
            if (moviesAdapter.snapshot().isNotEmpty()) {
                skeletonScreen.hide()
            }

        }


        fab_scrollUp.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
            hideFabScrollUp()
        }
    }

    private fun hideFabScrollUp() {
        fab_scrollUp.visibility = View.GONE
    }

    private fun showFabScrollUp() {
        fab_scrollUp.visibility = View.VISIBLE
    }

    private fun setupView() {
        lifecycleScope.launch {
            discoverViewModel.listData.collect {
                moviesAdapter.submitData(it)
            }
        }
    }


    override fun onItemClick(oneMovie: OneMovie) {
        val activity = requireActivity() as BottomMainActivity
        activity.onClickItem(oneMovie)
    }
}