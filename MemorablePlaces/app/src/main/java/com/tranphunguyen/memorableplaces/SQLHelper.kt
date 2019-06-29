package com.tranphunguyen.memorableplaces

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v4.os.IResultReceiver
import android.util.Log

class SQLHelper(context: Context) : SQLiteOpenHelper(context, "places", null, 1) {

    private val TABLE_NAME = "place"
    private val ID = "id"
    private val NAME = "name"
    private val LONGITUDE = "longitude"
    private val LATITUDE = "latitude"

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " integer primary key autoincrement not null, " +
                NAME + " TEXT, " +
                LATITUDE + " REAL, " +
                LONGITUDE + " REAL)"

        db?.execSQL(sqlQuery)
        Log.d("TestPlace", "create database successful!")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addPlace(place: Place) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME, place.name)
        values.put(LATITUDE, place.latitude)
        values.put(LONGITUDE, place.longitude)

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun getAllPlace(): ArrayList<Place> {
        val listPlace = ArrayList<Place>()

        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.writableDatabase

        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val place = Place(cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3))
                listPlace.add(place)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return listPlace
    }
}