package com.tranphunguyen.mynote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var adapter: NoteAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    var sharedPreferences: SharedPreferences? = null

    var listNote = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("sharedPrefNote", Context.MODE_PRIVATE)

        listNote = getNotes()

        adapter = NoteAdapter(this, listNote)

        Log.d("TestNote", listNote.size.toString())

        linearLayoutManager = LinearLayoutManager(this)

        noteRecyclerview.layoutManager = linearLayoutManager
        noteRecyclerview.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.setNotes(getNotes())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.add_note -> {

                val intent = Intent(this, NoteEditorActivity::class.java)
                startActivity(intent)

            }
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main_menu, menu)
        return true
    }


    private fun getNotes(): ArrayList<String> {
        val hashSetNotes = sharedPreferences
            ?.getStringSet("notes", HashSet())

        if (hashSetNotes != null && hashSetNotes.isNotEmpty()) {

            val list = hashSetNotes.toMutableList() as ArrayList<String>
            Log.d("TestNote1", list.size.toString())

            return list
        }

        return ArrayList()
    }

}
