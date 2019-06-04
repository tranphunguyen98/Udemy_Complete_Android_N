package com.example.ntran.audioplayer

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var audioManager: AudioManager
    var timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer = MediaPlayer.create(this, R.raw.ai)
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        btnStart.setOnClickListener {
            mediaPlayer.start()
        }
        btnPause.setOnClickListener {
            mediaPlayer.pause()
        }
        seekbar.max = mediaPlayer.duration
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    seekbar.setProgress(mediaPlayer.currentPosition, true)
                }
            }
        }, 0, 1000)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.d("Test","Tocuh")
                timer.cancel()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.d("Test","STop")

                seekBar?.progress?.let {
                    mediaPlayer.seekTo(it)
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                seekbar.setProgress(mediaPlayer.currentPosition, true)
                            }
                        }
                    }, 0, 1000)
                }
            }

        })
    }
}
