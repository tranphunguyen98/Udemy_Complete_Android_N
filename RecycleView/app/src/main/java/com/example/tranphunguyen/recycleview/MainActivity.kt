package com.example.tranphunguyen.recycleview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*

class MainActivity : AppCompatActivity() {
    var list = MutableList(10) { it }

    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        recyclerView.adapter = NumberAdapter()
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
    }

    inner class NumberAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            if (viewType == 1) {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.no_data_item, parent, false)
                return NoDataCellViewHolder(itemView)
            }

            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            val viewHolder = NumberViewHolder(itemView)
            itemView.setOnClickListener {
                val position = viewHolder.adapterPosition
                if (position in 0 until list.size) {
                    list.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
            return viewHolder
        }

        override fun getItemCount(): Int = if (list.size > 0) list.size else 1

        override fun getItemViewType(position: Int): Int {
            return if (list.size > 0) 2 else 1
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, p1: Int) {
            if (viewHolder is NumberViewHolder) {
                viewHolder.itemView.name.text = list[p1].toString()
                viewHolder.itemView.imgAvatar.setImageResource(R.drawable.abc_ic_go_search_api_material)
            }
        }

        inner class NumberViewHolder(view: View) : RecyclerView.ViewHolder(view)
        inner class NoDataCellViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}
