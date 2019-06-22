package com.example.ntran.countdownegg

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isStart = true
    var maxTimer = 0
    var countDownTimer: CountDownTimer? = null
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDefault()

        mediaPlayer = MediaPlayer.create(this, R.raw.coi_hu)

        btnStart.setOnClickListener {
            when {
                isStart -> play()
                else -> pause()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvNumber.text = progress.toString()
                maxTimer = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                countDownTimer?.cancel()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                tvNumber.text = seekBar?.progress.toString()

                isStart = true
                btnStart.text = "Start"
            }

        })
    }

    private fun pause() {
        mediaPlayer?.pause()
        isStart = true
        btnStart.text = "Start"
        countDownTimer?.cancel()
        maxTimer = tvNumber.text.toString().toInt()
    }

    private fun setTimer() {
        countDownTimer = object : CountDownTimer(maxTimer * 1000L, 1000) {

            override fun onFinish() {
                mediaPlayer?.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                tvNumber.text = (millisUntilFinished / 1000).toString()
                seekBar.progress = (millisUntilFinished / 1000).toInt()
            }
        }
    }

    private fun play() {
        setTimer()

        countDownTimer?.start()
        isStart = false
        btnStart.text = "Stop"
    }

    private fun initDefault() {
        seekBar.max = 500
        tvNumber.text = 0.toString()
    }
}
