package com.example.cellcomtvassignment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cellcomtvassignment.model.Movie


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val isFavoritesListChanged: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    /**
     * Creating a simple table for favorites movies
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_MOVIE_ID + " INTEGER,"
                + KEY_NAME + " TEXT,"
                + KEY_OVERVIEW + " TEXT,"
                + KEY_POSTER_PATH + " TEXT"
                + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    /**
     * Called when we update the database
     */
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addMovieToFavorites(movie: Movie): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_MOVIE_ID, movie.id)
        contentValues.put(KEY_NAME, movie.title)
        contentValues.put(KEY_OVERVIEW, movie.overview)
        contentValues.put(KEY_POSTER_PATH, movie.posterPath)

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

    fun readMoviesFromDatabase(): HashMap<Int,Movie> {
        val returnedSet: HashMap<Int,Movie> = HashMap()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (exception: SQLiteException) {
            db.execSQL(selectQuery)
            return returnedSet
        }
        var id: Int
        var title: String
        var overview: String
        var posterPath: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MOVIE_ID))
                title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
                overview = cursor.getString(cursor.getColumnIndexOrThrow(KEY_OVERVIEW))
                posterPath = cursor.getString(cursor.getColumnIndexOrThrow(KEY_POSTER_PATH))
                val movie = Movie(
                    null,
                    null,
                    null,
                    id,
                    null,
                    null,
                    overview,
                    null,
                    posterPath,
                    null,
                    title,
                    null,
                    null,
                    null
                )
                returnedSet.put(id,movie)
            } while (cursor.moveToNext())
        }
        isFavoritesListChanged.postValue(true)
        return returnedSet
    }

    fun getFavoritesList(): MutableLiveData<HashMap<Int, Movie>> {
        val returnedSet: HashMap<Int,Movie> = readMoviesFromDatabase()
        val data: MutableLiveData<HashMap<Int,Movie>> = MutableLiveData<HashMap<Int,Movie>>()
        data.value = returnedSet
        return data
    }

    companion object {
        private const val DATABASE_NAME = "FavoritesDatabase"
        private const val TABLE_CONTACTS = "FavoritesTable"
        private const val DATABASE_VERSION = 2
        private const val KEY_ID = "_id"
        private const val KEY_NAME = "name"
        private const val KEY_OVERVIEW = "overview"
        private const val KEY_POSTER_PATH = "path"
        private const val KEY_MOVIE_ID = "movieId"
    }

}