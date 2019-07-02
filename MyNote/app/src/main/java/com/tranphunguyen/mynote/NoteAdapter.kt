package com.tranphunguyen.mynote

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES.N
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(val context: Context, var listNote: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        Log.d("TestNote", listNote.size.toString())

        return when (getItemViewType(p1)) {
            0 -> {
                val view = LayoutInflater.from(p0.context).inflate(R.layout.no_data_cell, p0, false)
                NoDataCellHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(p0.context).inflate(R.layout.item_note, p0, false)
                val noteHolder = NoteHolder(view)

                view.setOnClickListener {
                    val intent = Intent(p0.context as MainActivity, NoteEditorActivity::class.java)
                    intent.putExtra("noteId", noteHolder.adapterPosition)

                    context.startActivity(intent)
                }

                view.setOnLongClickListener {

                    val position = noteHolder.adapterPosition

                    listNote.removeAt(position)

                    notifyItemRemoved(position)

                    Log.d("TestNode", "Đã xóa $position")

                    true
                }
                noteHolder
            }

        }
    }

    override fun getItemCount(): Int = if (listNote.size > 0) listNote.size else 1

    override fun getItemViewType(position: Int): Int = if (listNote.size > 0) 1 else 0

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is NoteHolder) {
            p0.itemView.tvNote.text = listNote[p1]
        }
    }

    fun setNotes(notes: ArrayList<String>) {
        this.listNote = notes
        notifyDataSetChanged()
    }

}

class NoteHolder(view: View) : RecyclerView.ViewHolder(view)
class NoDataCellHolder(view: View) : RecyclerView.ViewHolder(view)