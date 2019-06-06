package com.example.ntran.braintrainer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var total = 0
    var currentPoint = 0
    var listResult = IntArray(4)
    var correctResult = 0
    var number1 = 0
    var number2 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay.setOnClickListener {
            it.visibility = View.INVISIBLE
            finalResult.visibility = View.INVISIBLE
            btn1.isEnabled = true
            btn2.isEnabled = true
            btn3.isEnabled = true
            btn4.isEnabled = true
            createRandomResult()
            displayResult()
            object : CountDownTimer(30000, 1000) {
                override fun onFinish() {
                    it.visibility = View.VISIBLE
                    finalResult.visibility = View.VISIBLE
                    btn1.isEnabled = false
                    btn2.isEnabled = false
                    btn3.isEnabled = false
                    btn4.isEnabled = false
                    finalResult.text = "Kết quả: " + currentPoint + "/" + total
                    total = 0
                    currentPoint = 0
                    result.text = "0/0"
                }

                override fun onTick(millisUntilFinished: Long) {
                    countDown.text = (millisUntilFinished / 1000).toString()
                }

            }.start()
        }
    }

    private fun createRandomResult() {
        number1 = Random.nextInt(100)
        number2 = Random.nextInt(100)

        correctResult = number1 + number2

        expression.text = "${number1} + ${number2}"

        val indexCorrect = Random.nextInt(4)

        listResult[indexCorrect] = correctResult

        for (i in listResult.indices) {
            if(i != indexCorrect) {
                listResult[i] = Random.nextInt(200)
            } else {
                listResult[i] = correctResult
            }
        }
    }

    private fun displayResult() {
        btn1.text = listResult[0].toString()
        btn2.text = listResult[1].toString()
        btn3.text = listResult[2].toString()
        btn4.text = listResult[3].toString()
    }

    fun check(v: View) {
        if (v is Button) {
            if (v.text.toString().toInt() == correctResult) {
                total++
                currentPoint++
            } else {
                total++
            }
            result.text = "${currentPoint}/${total}"
            createRandomResult()
            displayResult()
        }
    }


}
