package com.tranphunguyen.memorableplaces

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPrefs private constructor() {

    private val sharedPreferences: SharedPreferences by lazy {
        App.self().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFS_NAME = "places"
        private const val PREFS_PLACE = "places"

        private var instance: SharedPrefs? = null

        fun getInstance(): SharedPrefs {
            if (instance == null) {
                instance = SharedPrefs()
            }
            return instance as SharedPrefs
        }
    }

    fun addPlace(place: String) {
        val editor = this.sharedPreferences.edit()

        val listPlace = getPlaces()

        listPlace.add(place)

        editor.putStringSet(PREFS_PLACE, listPlace.toHashSet())

        editor.apply()

        Log.d("TestPlace", "Put thành công $place")
    }

    fun savePlaces(hashSet: HashSet<String>) {


    }

    fun getPlaces(): ArrayList<String> {
        val list = this.sharedPreferences.getStringSet(PREFS_PLACE, HashSet<String>())

        if (list != null && list.isNotEmpty()) {
            return list.toMutableList() as ArrayList<String>
        }

        return ArrayList()
    }

}