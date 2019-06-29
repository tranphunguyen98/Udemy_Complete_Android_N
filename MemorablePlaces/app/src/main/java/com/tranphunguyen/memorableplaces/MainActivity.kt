package com.tranphunguyen.memorableplaces

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.SqliteObjectLeakedViolation
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val PLACE_PICKER_REQUEST = 1
    private lateinit var sqlHelper: SQLHelper

    lateinit var list: ArrayList<Place>
    private var adapter: PlacesAdapter? = null
    private val sharedPrefs = SharedPrefs.getInstance()

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallBack = object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                adapter?.remove(viewHolder.adapterPosition)
                adapter?.notifyItemRemoved(viewHolder.adapterPosition)

            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = recyclerView.adapter as PlacesAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                Log.d("TestAdapter", "$from to $to")

                adapter.moveItem(from, to)
                adapter.notifyItemMoved(from, to)

                return true
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)

                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.7f
                }
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val dragFlags = UP or DOWN
                val swipeFlags = START or END

                return Callback.makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }


        }

        Log.d("TestAdapter", "item")

        ItemTouchHelper(simpleItemTouchCallBack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqlHelper = SQLHelper(this)

        list = sqlHelper.getAllPlace()

        title = "Danh sách địa điểm"

        val linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = linearLayoutManager
        adapter = PlacesAdapter(this, list)
        recyclerView.adapter = adapter

        itemTouchHelper.attachToRecyclerView(recyclerView)

        btnAddPlace.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)

            startActivity(intent)
        }

    }

    fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onStart() {
        super.onStart()

        list = sqlHelper.getAllPlace()

        adapter?.setListPlace(list)

    }
}
