package com.kangaroo.filmore.Views.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kangaroo.filmore.Models.OneMovie
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

   val viewModel: DetailViewModel by viewModels()

    lateinit var poster: ImageView
    lateinit var titleText: TextView
    lateinit var originalTitleText: TextView
    lateinit var releaseTextView: TextView
    lateinit var overView: TextView
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    private var clickListener: View.OnClickListener? = null
    var seconds: Float? = null
    lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initViewsAndProperties()

        val oneMovie = intent?.getParcelableExtra<OneMovie?>("oneMovie")

        youTubePlayerView = findViewById(R.id.youtube_player_view)

        this.lifecycle.addObserver(youTubePlayerView)

        setDataToView(oneMovie)

        showPlayer(0, youTubePlayerView, oneMovie)



        clickListener = View.OnClickListener {
            when (it.id) {
                R.id.button1 -> {
                    button1.isEnabled = false
                    button2.isEnabled = true
                    button3.isEnabled = true
                }
                R.id.button2 -> {
                    button1.isEnabled = true
                    button2.isEnabled = false
                    button3.isEnabled = true
                }
                R.id.button3 -> {
                    button1.isEnabled = true
                    button2.isEnabled = true
                    button3.isEnabled = false
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setDataToView(oneMovie: OneMovie?) {
        if (oneMovie?.first_air_date == null){
            releaseTextView.text = "Релиз:" + oneMovie?.release_date
        } else releaseTextView.text = "Дата первого показа:" + oneMovie.first_air_date

            title = oneMovie?.title
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500/" + oneMovie?.poster_path)
                .into(poster)
            titleText.text = oneMovie?.title
            originalTitleText.text = "Оригинальное название: " + oneMovie?.original_title
            overView.text = "Краткое описание: " + oneMovie?.overview


    }

    private fun initViewsAndProperties() {

        poster = findViewById(R.id.poster_detail)
        titleText = findViewById(R.id.title_detail)
        originalTitleText = findViewById(R.id.title_original_detail)
        releaseTextView = findViewById(R.id.release_detail)
        overView = findViewById(R.id.overview_detail)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        button1.setOnClickListener(clickListener)
        button2.setOnClickListener(clickListener)
        button3.setOnClickListener(clickListener)
    }

    private fun showPlayer(number: Int, youTubePlayerView: YouTubePlayerView, oneMovie: OneMovie?) {
        viewModel.getVideoOfVideo(oneMovie?.id.toString())
        viewModel.movieVideo.observe(this, {
            if (it?.isNotEmpty() == true) {
                Log.d(LOG_TAG, it.toString())
                val intent_toFullScreen =
                    Intent(this@DetailActivity, FullScreenActivityYouTube::class.java)
                youTubePlayerView.visibility = View.VISIBLE
                showButtons(it.size)
                youTubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = it[number].key.toString()
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
                        val videoId = it[number].key.toString()
                        intent_toFullScreen.putExtra(Constants.MOVIE_ID, videoId)
                        startActivityForResult(intent_toFullScreen, 1)
                    }

                    override fun onYouTubePlayerExitFullScreen() {}
                })
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data ==  null){return}
        seconds = data.getFloatExtra(Constants.DATA_RESULT_FULLSCREEN, 0.0f)
        Log.d(LOG_TAG, "seconds is ^ $seconds")
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {
                super.onPlaybackQualityChange(youTubePlayer, playbackQuality)
                youTubePlayer.seekTo(seconds?: 0f)
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




}
