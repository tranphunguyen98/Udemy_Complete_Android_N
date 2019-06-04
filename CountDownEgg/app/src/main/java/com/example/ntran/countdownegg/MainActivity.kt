package com.example.ntran.countdownegg

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.SeekBar
import com.example.ntran.countdownegg.R.id.seekBar
import com.example.ntran.countdownegg.R.id.tvNumber
import kotlinx.android.synthetic.main.activity_main.*

var isStart = true


class MainActivity : AppCompatActivity() {

    var maxTimer = 0
    lateinit var countDownTimer: CountDownTimer
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mediaPlayer = MediaPlayer.create(this, R.raw.coi_hu)

        btnStart.setOnClickListener {

            if (isStart) {

                countDownTimer = object : CountDownTimer(maxTimer * 1000L, 1000) {

                    override fun onFinish() {
                        Log.d("OnFinish", "Finish")
                        mediaPlayer.start()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        tvNumber.text = (millisUntilFinished / 1000).toString()
                        seekBar.progress = (millisUntilFinished / 1000).toInt()
                    }
                }
                countDownTimer.start()
                isStart = false
                btnStart.text = "Stop"
            } else {
                mediaPlayer.pause()
                isStart = true
                btnStart.text = "Start"
                countDownTimer.cancel()
                maxTimer = tvNumber.text.toString().toInt()
            }
        }
        seekBar.max = 500
        tvNumber.text = 0.toString()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvNumber.text = progress.toString()
                maxTimer = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                countDownTimer.cancel()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                tvNumber.text = seekBar?.progress.toString()
                countDownTimer = object : CountDownTimer(maxTimer * 1000L, 1000) {
                    override fun onFinish() {
                        mediaPlayer.start()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        tvNumber.text = (millisUntilFinished / 1000).toString()
                        seekBar?.progress = (millisUntilFinished / 1000).toInt()
                    }
                }
                isStart = true
                btnStart.text = "Start"
            }

        })
    }
}
