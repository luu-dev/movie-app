package com.example.android_3.ui.playvideo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import com.example.android_3.R
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.android_3.api.urlApi
import com.example.android_3.databinding.PlayervideoBinding
import com.example.basemodule.base.base.BaseActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.playervideoitem.view.*

class PlayerVideoActivity : BaseActivity<PlayervideoBinding, PlayerVideoViewModel>() {
    override val viewModelClass: Class<PlayerVideoViewModel>?
        get() = PlayerVideoViewModel::class.java
    override val layoutId: Int = R.layout.playervideo

    private var player: SimpleExoPlayer? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playBackPosition = 0L

    companion object {
        var fullscreen = false
        val TAG = "TAG"
        var movie_id =""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent:Intent = getIntent()
        movie_id = intent.getStringExtra("movie_id")!!;
        binding!!.videoView.wd.setOnClickListener {
            var params = binding!!.videoView.layoutParams as LinearLayout.LayoutParams

            if (fullscreen) {
                binding!!.videoView.wd.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.exo_controls_fullscreen_enter
                )

                window?.decorView?.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_VISIBLE
                if (actionBar != null) actionBar?.show()
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height =
                    (200 * applicationContext?.resources?.displayMetrics?.density!!).toInt()
                binding!!.videoView.layoutParams = params
                fullscreen = false

            } else {
                binding!!.videoView.wd.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.exo_controls_fullscreen_exit
                )
                window?.decorView
                    ?.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                if (actionBar != null) actionBar?.hide()
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                binding!!.videoView.layoutParams = params
                fullscreen = true

            }

        }
    }


    private fun initPlayer() {
        if (player == null) {
            val adaptiveTrackSelection: TrackSelection.Factory =
                AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())
            player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(this),
                DefaultTrackSelector(adaptiveTrackSelection),
                DefaultLoadControl()
            )
        }
        binding!!.videoView.player = player
        var defaultBandwidthMeter = DefaultBandwidthMeter()
        var dataSourceFactory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(this, "Android_3"),
            defaultBandwidthMeter
        )
        val uri =
            Uri.parse(urlApi.BASE_URL +"videos/"+movie_id+ "/filename.m3u8")
        var handler = Handler()
        var mediaSource = HlsMediaSource(uri, dataSourceFactory, handler, null)
        player?.prepare(mediaSource)
        player?.playWhenReady = playWhenReady
        player?.run {
            // player.prepare(mediaSource)

            addListener(object : Player.EventListener {
                override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                }

                override fun onSeekProcessed() {
                }

                override fun onTracksChanged(
                    trackGroups: TrackGroupArray?,
                    trackSelections: TrackSelectionArray?
                ) {
                }

                override fun onPlayerError(error: ExoPlaybackException?) {
                }

                override fun onLoadingChanged(isLoading: Boolean) {
                }

                override fun onPositionDiscontinuity(reason: Int) {
                }


                override fun onRepeatModeChanged(repeatMode: Int) {
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                }

                override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    when (playbackState) {
                        ExoPlayer.STATE_READY -> binding!!.videoView.loading.visibility = View.GONE
                        ExoPlayer.STATE_BUFFERING -> binding!!.videoView.loading.visibility =
                            View.VISIBLE
                    }
                }

            })
            seekTo(currentWindow, playBackPosition)
            prepare(mediaSource, false, false)
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            player?.let {
                playBackPosition = it.currentPosition
                currentWindow = it.currentWindowIndex
                playWhenReady = it.playWhenReady
                it.release()

            }
            player = null
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23)
            initPlayer()

    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 && player == null)
            initPlayer()

    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) releasePlayer()

    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) releasePlayer()

    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        binding!!.videoView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        )
    }
}

