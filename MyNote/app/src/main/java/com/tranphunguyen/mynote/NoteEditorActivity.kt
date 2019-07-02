package com.tranphunguyen.mynote

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_note_editor.*

class NoteEditorActivity : AppCompatActivity(), View.OnClickListener {

    val CREATE = 0
    val EDIT = 1
    var mode = CREATE

    var id = -1
    var listNote = ArrayList<String>()

    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)

        sharedPreferences = getSharedPreferences("sharedPrefNote", Context.MODE_PRIVATE)

        id = intent.getIntExtra("noteId", -1)

        listNote = getNotes()

        if (id > -1) {
            mode = EDIT
            edtNote.setText(listNote[id])
            Log.d("TestNoteID", id.toString())

        } else {
            mode = CREATE
        }

        btnSave.setOnClickListener(this)
    }

    private fun getNotes(): ArrayList<String> {
        return sharedPreferences
            ?.getStringSet("notes", HashSet())
            ?.toMutableList() as ArrayList<String>
    }

    private fun saveNotes(notes: ArrayList<String>) {

        sharedPreferences?.edit()?.putStringSet("notes", notes.toHashSet())?.apply()

    }

    private fun saveNote(note: String) {
        when (mode) {
            CREATE -> {
                listNote.add(note)
            }
            EDIT -> {
                Log.d("TestNoteID", id.toString())
                listNote[id] = note
            }
        }

        saveNotes(listNote)

        Toast.makeText(this, "Lưu thành công!\n $note", Toast.LENGTH_SHORT).show()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSave -> {
                val strNote = edtNote.text.toString()
                saveNote(strNote)
            }

        }
    }
}



