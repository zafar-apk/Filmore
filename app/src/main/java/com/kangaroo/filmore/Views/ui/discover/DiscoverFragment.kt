package com.kangaroo.filmore.Views.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.Constants.LOG_TAG
import com.kangaroo.filmore.Utils.MovieAdapter
import com.kangaroo.filmore.Utils.OnItemClickListener
import com.kangaroo.filmore.Views.BottomMainActivity

class DiscoverFragment : Fragment(), OnItemClickListener {

    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var adapter: MovieAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_discover, container, false)

        setRecyclerView(root)
        val skeletonScreen = Skeleton.bind(recyclerView)
            .adapter(adapter)
            .load(R.layout.skeleton_movie_discover)
            .show()

        discoverViewModel.listData.observe(viewLifecycleOwner, {
            skeletonScreen.hide()
            adapter.submitList(it)
        })

        return root
    }

    private fun setRecyclerView(view: View) {
        adapter = MovieAdapter(this)
        recyclerView = view.findViewById(R.id.discover_recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onItemClick(id: String, oneMovie: OneMovie){
        val activity = activity as BottomMainActivity
        activity.onClickItem(id, oneMovie)
        Log.d(LOG_TAG, "idd = $id")
        }




}