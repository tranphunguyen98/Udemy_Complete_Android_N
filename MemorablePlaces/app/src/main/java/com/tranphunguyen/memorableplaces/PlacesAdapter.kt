package com.tranphunguyen.memorableplaces

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*

class PlacesAdapter(var context: Context, private var listPlace: ArrayList<Place>) :


    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 0) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.no_cell, parent, false)
            return NoCellViewHolder(itemView)

        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)

            val viewHolder = PlaceViewHolder(itemView)

            itemView.setOnClickListener {
                Log.d("TestAdapter", listPlace[viewHolder.adapterPosition].name)

                val intent = Intent(context as MainActivity, MapsActivity::class.java)

                intent.putExtra("place", listPlace[viewHolder.adapterPosition])

                context.startActivity(intent)
            }

            viewHolder.itemView.handleView.setOnTouchListener { _, event ->

                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    val activity = (parent.context) as MainActivity
                    activity.startDragging(viewHolder)
                }
                return@setOnTouchListener true
            }

            return viewHolder
        }
    }

    override fun getItemViewType(position: Int): Int = if (listPlace.size > 0) 1 else 0

    override fun getItemCount(): Int = if (listPlace.size > 0) listPlace.size else 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlaceViewHolder) {
            holder.itemView.namePlace.text = listPlace[position].name
        }
    }

    fun moveItem(from: Int, to: Int) {
        if (listPlace.size > 0) {
            if (from > to) {
                val temp = listPlace[from]

                for (i in from downTo to + 1) {
                    listPlace[i] = listPlace[i - 1]
                }

                listPlace[to] = temp
            } else {
                val temp = listPlace[from]

                for (i in from until to) {
                    listPlace[i] = listPlace[i + 1]
                }

                listPlace[to] = temp
            }
        }
    }

    fun remove(position: Int) {
        if (listPlace.size > 0) {
            listPlace.removeAt(position)
        }
    }

    fun setListPlace(listPlace: ArrayList<Place>) {
        this.listPlace = listPlace

        this.notifyDataSetChanged()
    }

    class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class NoCellViewHolder(view: View) : RecyclerView.ViewHolder(view)
}