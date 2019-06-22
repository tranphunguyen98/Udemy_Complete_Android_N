package com.example.tranphunguyen.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item.view.*

class AdapterListview(private val context: Context, private val list: ArrayList<Int>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
            viewHolder = ViewHolder()
            viewHolder.avatar = view.imgAvatar
            viewHolder.name = view.name

            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.avatar.setImageResource(android.R.drawable.alert_dark_frame)
        viewHolder.name.text = list[position].toString()
        return view
    }

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = list[position].toLong()

    override fun getCount(): Int = list.size

    internal class ViewHolder {
        lateinit var avatar: ImageView
        lateinit var name: TextView
    }
}