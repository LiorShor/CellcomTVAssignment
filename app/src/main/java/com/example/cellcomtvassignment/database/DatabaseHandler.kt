package com.example.cellcomtvassignment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.MutableLiveData


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val isFavoritesListChanged : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
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

    fun addMovieToFavorites(movieId: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_MOVIE_ID, movieId)

        //Inserting row to the table
        val success = db.insert(TABLE_CONTACTS, null, contentValues)

        //closing the db connection
        db.close()
        isFavoritesListChanged.postValue(true)
        return success
    }

    fun removeMovieFromFavorites(movieId: Int): Boolean {
        val db = this.writableDatabase
        isFavoritesListChanged.postValue(true)
        return db.delete(TABLE_CONTACTS, "$KEY_MOVIE_ID=$movieId", null) > 0
    }

    fun readMoviesFromDatabase(): Set<Int> {
        val returnedSet: HashSet<Int> = HashSet()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (exception: SQLiteException) {
            db.execSQL(selectQuery)
            return returnedSet
        }
        if (cursor.moveToFirst()) {
            do {
                returnedSet.add(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MOVIE_ID)))
            } while (cursor.moveToNext())
        }
        isFavoritesListChanged.postValue(true)
        return returnedSet
    }

    fun getFavoritesList(): MutableLiveData<Set<Int>> {
        val returnedSet: Set<Int> = readMoviesFromDatabase()
        val data: MutableLiveData<Set<Int>> = MutableLiveData<Set<Int>>()
        data.value = returnedSet
        return data
    }

    companion object {
        private const val DATABASE_NAME = "FavoritesDatabase"
        private const val TABLE_CONTACTS = "FavoritesTable"
        private const val DATABASE_VERSION = 1
        private const val KEY_ID = "_id"
        private const val KEY_MOVIE_ID = "movieId"
    }

}