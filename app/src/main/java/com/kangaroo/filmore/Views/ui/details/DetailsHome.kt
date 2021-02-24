package com.kangaroo.filmore.Views.ui.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.Constants
import com.kangaroo.filmore.Utils.Constants.LOG_TAG
import com.kangaroo.filmore.Utils.OnItemClickListener
import com.kangaroo.filmore.Utils.PagedMovieAdapter
import com.kangaroo.filmore.Views.BottomMainActivity
import com.kangaroo.filmore.Views.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsHome : AppCompatActivity(), OnItemClickListener {

    private val detailsHomeViewModel: HomeDetailsViewModel by viewModels()
    var recyclerView: RecyclerView? = null
    var adapterRecycler: PagedMovieAdapter? = null
    var contentType: String? = null
    var timeWindow: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_home)

        adapterRecycler = PagedMovieAdapter(this)
        recyclerView = findViewById(R.id.recycler_home_details)

        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterRecycler
        }


        
            if (intent?.getStringExtra(Constants.DATA_MEDIA_TYPE) == Constants.MEDIA_TYPE_MOVIE){
                contentType = "movie"
                when {
                    intent?.getIntExtra(Constants.DATA_POSITION, -1) == 0 -> {
                        timeWindow = "day"
                        title = "Топ фильмов за сегодня"
                        detailsHomeViewModel.setDataToViewModel(contentType!!, timeWindow!!)
                    }
                    intent?.getIntExtra(Constants.DATA_POSITION, -1) == 1 -> {
                        timeWindow = "week"
                        val s = "Топ фильмов за неделю"
                        title = s
                        detailsHomeViewModel.setDataToViewModel(contentType!!, timeWindow!!)
                    }
                    else -> {
                        finish()
                        Toast.makeText(applicationContext, "Произошла непредвиденная ошибка", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (intent?.getStringExtra(Constants.DATA_MEDIA_TYPE) == Constants.MEDIA_TYPE_TV){
                contentType = "tv"
                when {
                    intent?.getIntExtra(Constants.DATA_POSITION, -1) == 0 -> {
                        timeWindow = "day"
                        title = "Топ передач за сегодня"
                        Log.d(LOG_TAG, "wooooooork")
                        detailsHomeViewModel.setDataToViewModel(contentType!!, timeWindow!!)
                    }
                    intent?.getIntExtra(Constants.DATA_POSITION, -1) == 1 -> {
                        timeWindow = "week"
                        title = "Топ передач за неделю"
                        detailsHomeViewModel.setDataToViewModel(contentType!!, timeWindow!!)
                    }
                    else -> {
                        finish()
                        Toast.makeText(
                            applicationContext,
                            "Произошла непредвиденная ошибка",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

lifecycleScope.launch {
    detailsHomeViewModel.listData?.collect {
        adapterRecycler?.submitData(it)
    }
}

    }

    override fun onItemClick(oneMovie: OneMovie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("oneMovie", oneMovie)
        startActivity(intent)
    }
}