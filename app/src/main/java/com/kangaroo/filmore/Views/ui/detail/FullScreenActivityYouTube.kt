package com.kangaroo.filmore.Views.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView



class FullScreenActivityYouTube : AppCompatActivity() {

    lateinit var intentData:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_you_tube)

        intentData = Intent()

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view_fullscreen)
        this.lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                intentData.putExtra(Constants.DATA_RESULT_FULLSCREEN, second)
            }
        })
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                val movieId = intent.getStringExtra(Constants.MOVIE_ID)
                val startsTime = intent.getFloatExtra(Constants.MOVIE_STARTS_TIME, 0f)
                if (movieId != null) {
                    youTubePlayer.loadVideo(movieId, startsTime)
                }
            }
        })
        youTubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                Toast.makeText(
                    this@FullScreenActivityYouTube,
                    "Нажите ещё раз для выхода из полноэкранного режима",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onYouTubePlayerExitFullScreen() {
                setResult(RESULT_OK, intentData)
                finish()
            }
        })
    }
}