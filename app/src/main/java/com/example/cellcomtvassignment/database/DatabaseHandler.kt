package com.example.cellcomtvassignment.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /**
     * Creating a simple table for favorites movies
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MOVIE_ID + " INTEGER" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    /**
     * Called when we update the database
     */
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addMovie(movieId : Int) {

    }

    companion object {
        private const val DATABASE_NAME = "FavoritesDatabase"
        private const val TABLE_CONTACTS = "FavoritesTable"
        private const val DATABASE_VERSION = 1
        private const val KEY_ID = "_id"
        private const val KEY_MOVIE_ID = "movieId"

    }

}