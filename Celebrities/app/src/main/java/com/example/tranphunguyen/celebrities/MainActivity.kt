package com.example.tranphunguyen.celebrities

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    data class Item(var Name: String, var Image: String)

    private var listItem: ArrayList<Item>? = null
    private var listItemInGame = ArrayList<Item>(4)
    private var indexCorrect = 0
    private var iSelectCorrect = 0

    class DownloadHtml : AsyncTask<String, Void, ArrayList<Item>?>() {
        private var listItem = ArrayList<Item>()
        override fun doInBackground(vararg params: String?): ArrayList<Item>? {
            var document: Document
            try {
                document = Jsoup.connect(params[0]).get()
                if (document != null) {
                    var elements = document.select("div.chart-list-item")
                    Log.e("Celebrities", elements.size.toString())
                    for (element in elements) {

                        val imgElement = element.select("img")

                        val item = Item("", "")
//
                        val nameElement = element.select("span.chart-list-item__title-text")

                        var img = imgElement.attr("data-srcset")
                        if (img.trim().isNotEmpty()) {
                            img = img.substring(img.lastIndexOf(',') + 2, img.lastIndexOf(' '))
                        } else {
                            if (imgElement.attr("data-srcset").isNotEmpty()) {
                                img = imgElement.attr("data-srcset")
                            }
                        }

                        item.Image = img
                        if (nameElement.text().isNotEmpty()) {
                            item.Name = nameElement.text()
                        }

                        listItem.add(item)
                    }

                }
                return listItem
            } catch (exception: Exception) {
                exception.printStackTrace()
                Log.e("Celebrities", "\"Fail: \" + ${exception.message}")
                return listItem
            }
        }

        override fun onPostExecute(result: ArrayList<Item>?) {

        }

        override fun onCancelled() {
            Log.e("Celebrities", "Cancelled ")
            super.onCancelled()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for(i in 0..3) {
            listItemInGame.add(Item("",""))
        }
        listItem = DownloadHtml().execute("https://www.billboard.com/charts/artist-100").get()
        createGame()
    }

    fun handle(v : View) {
        if(v is Button) {
            if(v.text.equals(listItemInGame[iSelectCorrect].Name)) {
                Toast.makeText(this,"Đúng rồi đấy, đó là: ${listItemInGame[iSelectCorrect].Name}",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Sai rồi :( đó là: ${listItemInGame[iSelectCorrect].Name} mới phải",Toast.LENGTH_LONG).show()
            }
        }
        createGame()
    }
    private fun createGame() {
        indexCorrect = Random.nextInt(listItem?.size!!)
        iSelectCorrect = Random.nextInt(4)
        for (i in 0..3) {
            var iRandom: Int

            if (i != iSelectCorrect) {
                do {
                    iRandom = Random.nextInt(100)
                } while (iRandom == indexCorrect)

            } else {
                iRandom = indexCorrect
            }

            listItemInGame[i] = listItem!![iRandom]
        }

        button1.text = listItemInGame[0].Name
        button2.text = listItemInGame[1].Name
        button3.text = listItemInGame[2].Name
        button4.text = listItemInGame[3].Name

        Glide
            .with(this)
            .load(listItem!![indexCorrect].Image)
            .placeholder(R.drawable.user)
            .into(imgAvatar)
    }
}
