package com.kangaroo.filmore.Views.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.Models.VideoOneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.Constants
import com.kangaroo.filmore.Utils.Constants.LOG_TAG
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.squareup.picasso.Picasso


class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    lateinit var poster: ImageView
    lateinit var backDropImageView: ImageView
    lateinit var titleText: TextView
    lateinit var originalTitleText: TextView
    lateinit var releaseTextView: TextView
    lateinit var overView: TextView
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    var seconds: Float? = null
    private var youTubePlayerView: YouTubePlayerView? = null
    private var youTubePlayerView2: YouTubePlayerView? = null
    private var youTubePlayerView3: YouTubePlayerView? = null
    private var fullScreenPlayer: YouTubePlayerView? = null
    lateinit var videoOneMovie: MutableList<VideoOneMovie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        initViewsAndProperties()

        val oneMovie = intent?.getParcelableExtra<OneMovie?>("oneMovie")

        youTubePlayerView?.let { this.lifecycle.addObserver(it) }
        youTubePlayerView2?.let { this.lifecycle.addObserver(it) }
        youTubePlayerView3?.let { this.lifecycle.addObserver(it) }

        setDataToView(oneMovie)

        viewModel.getVideoOfVideo(oneMovie?.id.toString())

        viewModel.movieVideo.observe(this, {
            showButtons(it.size)
            videoOneMovie = it
        })


    }

    @SuppressLint("SetTextI18n")
    private fun setDataToView(oneMovie: OneMovie?) {
        if (oneMovie?.first_air_date == null) {
            releaseTextView.text = "Релиз: " + oneMovie?.release_date
            title = oneMovie?.title
            titleText.text = oneMovie?.title
            originalTitleText.text = "Оригинальное название: " + oneMovie?.original_title
        } else {
            releaseTextView.text = "Дата первого показа: " + oneMovie.first_air_date
            title = oneMovie.name
            titleText.text = oneMovie.name
            originalTitleText.text = "Оригинальное название: " + oneMovie.original_name
        }
//        Picasso.get()
//            .load("https://image.tmdb.org/t/p/w500/" + oneMovie?.poster_path)
//            .into(poster)

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500/" + oneMovie?.backdrop_path)
            .into(backDropImageView)

        overView.text = "Краткое описание: " + oneMovie?.overview


    }

    private fun initViewsAndProperties() {

        titleText = findViewById(R.id.title_detail)
        originalTitleText = findViewById(R.id.title_original_detail)
        releaseTextView = findViewById(R.id.release_detail)
        overView = findViewById(R.id.overview_detail)
        backDropImageView = findViewById(R.id.back_drop_detail)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        youTubePlayerView = findViewById(R.id.youtube_player_view)
        youTubePlayerView2 = findViewById(R.id.second_youtube_player_view)
        youTubePlayerView3 = findViewById(R.id.third_youtube_player_view)

        button1.setOnClickListener {
            youTubePlayerView = findViewById(R.id.youtube_player_view)
            youTubePlayerView2?.release()
            youTubePlayerView3?.release()
            showPlayerByNumber(1)
            hidePlayerByNumber(2)
            hidePlayerByNumber(3)
            button1.isEnabled = false
            button2.isEnabled = true
            button3.isEnabled = true
            if (youTubePlayerView != null) {
                showPlayer(1, youTubePlayerView!!)
            }

        }
        button2.setOnClickListener {
            youTubePlayerView2 = findViewById(R.id.second_youtube_player_view)
            youTubePlayerView?.release()
            youTubePlayerView3?.release()
            hidePlayerByNumber(1)
            showPlayerByNumber(2)
            hidePlayerByNumber(3)
            button1.isEnabled = true
            button2.isEnabled = false
            button3.isEnabled = true
            if (youTubePlayerView2 != null) {
                showPlayer(2, youTubePlayerView2!!)
            }

        }
        button3.setOnClickListener {
            youTubePlayerView3 = findViewById(R.id.third_youtube_player_view)
            youTubePlayerView2?.release()
            youTubePlayerView?.release()
            hidePlayerByNumber(1)
            hidePlayerByNumber(2)
            showPlayerByNumber(3)
            button1.isEnabled = true
            button2.isEnabled = true
            button3.isEnabled = false
            if (youTubePlayerView3 != null) {
                showPlayer(3, youTubePlayerView3!!)
            }

        }


    }

    private fun showPlayer(number: Int, youTubePlayerView: YouTubePlayerView) {
        val intent_toFullScreen =
            Intent(this@DetailActivity, FullScreenActivityYouTube::class.java)
        youTubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = videoOneMovie[number - 1].key.toString()
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
        youTubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                intent_toFullScreen.putExtra(Constants.MOVIE_STARTS_TIME, second)
            }
        })
        youTubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                val videoId = videoOneMovie[number - 1].key.toString()
                intent_toFullScreen.putExtra(Constants.MOVIE_ID, videoId)
                fullScreenPlayer = youTubePlayerView
                startActivityForResult(intent_toFullScreen, 1)
            }

            override fun onYouTubePlayerExitFullScreen() {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        seconds = data.getFloatExtra(Constants.DATA_RESULT_FULLSCREEN, 0.0f)
        Log.d(LOG_TAG, "seconds is ^ $seconds")
        fullScreenPlayer?.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {
                super.onPlaybackQualityChange(youTubePlayer, playbackQuality)
                youTubePlayer.seekTo(seconds ?: 0f)
                youTubePlayer.play()
            }

        })
    }


    private fun showButtons(count: Int) {
        when (count) {
            1 -> button1.visibility = View.VISIBLE
            2 -> {
                button1.visibility = View.VISIBLE
                button2.visibility = View.VISIBLE
            }
            3 -> {
                button1.visibility = View.VISIBLE
                button2.visibility = View.VISIBLE
                button3.visibility = View.VISIBLE
            }
        }
    }

    private fun showPlayerByNumber(number: Int) {
        when (number) {
            1 -> youTubePlayerView?.visibility = View.VISIBLE
            2 -> youTubePlayerView2?.visibility = View.VISIBLE
            3 -> youTubePlayerView3?.visibility = View.VISIBLE
        }
    }

    private fun hidePlayerByNumber(number: Int) {
        when (number) {
            1 -> youTubePlayerView?.visibility = View.GONE
            2 -> youTubePlayerView2?.visibility = View.GONE
            3 -> youTubePlayerView3?.visibility = View.GONE

        }
    }
}
