package com.example.ntran.tictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var arrayNode = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
    private val arrayWin = arrayListOf(
        arrayListOf(0, 1, 2), arrayListOf(3, 4, 5), arrayListOf(6, 7, 8),
        arrayListOf(0, 3, 6), arrayListOf(1, 4, 7), arrayListOf(2, 5, 8),
        arrayListOf(0, 4, 8), arrayListOf(2, 4, 6)
    )
    var styleUser = 0
    var isContinue = true

    fun dropIn(v: View) {
        val indexOfTable = v.tag.toString().toInt()

        Log.d(
            "Test111",
            indexOfTable.toString() + ":" + arrayNode[indexOfTable].toString() + ":" + isContinue.toString()
        )
        val imgV = v as ImageView
        if (arrayNode[indexOfTable] == -1 && isContinue) {
            if (styleUser == 0) {
                imgV.setImageResource(R.drawable.red)
                imgV.translationY = -1000f
                imgV.animate().rotation(3600f).translationYBy(1000f).duration = 300
                styleUser = 1
            } else {
                imgV.setImageResource(R.drawable.yellow)
                imgV.scaleX = 0.1f
                imgV.scaleY = 0.1f
                imgV.animate().scaleXBy(1f).scaleYBy(1f).duration = 300
                styleUser = 0
            }

            arrayNode[indexOfTable] = styleUser

            for (it in arrayWin) {
                if (arrayNode[it[0]] == arrayNode[it[1]] && arrayNode[it[1]] == arrayNode[it[2]] && arrayNode[it[0]] != -1) {
                    if (styleUser == 0) {
                        Toast.makeText(this, "Vàng chiến thắng!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Đỏ chiến thắng!", Toast.LENGTH_LONG).show()
                    }
                    reset()
                    break
                }
            }

            if (!arrayNode.contains(-1)) {
                Toast.makeText(this, "Hòa rồi nhé!", Toast.LENGTH_LONG).show()
                reset()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun reset() {
        for (i in 0..this.main.childCount) {
            val view = this.main.getChildAt(i)
            if (view is AppCompatImageView) {
                view.setImageResource(0)
            }
        }

        isContinue = true
        arrayNode = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
        styleUser = 0
        img_board.setImageResource(R.drawable.board)
    }
}

