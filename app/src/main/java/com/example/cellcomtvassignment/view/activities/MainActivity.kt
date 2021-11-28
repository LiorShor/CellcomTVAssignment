package com.example.cellcomtvassignment.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cellcomtvassignment.R
import com.example.cellcomtvassignment.databinding.ActivityMainBinding
import com.example.cellcomtvassignment.model.MoviesRepository

class MainActivity : AppCompatActivity() {
    private lateinit var mMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)
        openMoviesActivity()
    }

    private fun openMoviesActivity() {
        val intent = Intent(this, MoviesMainActivity::class.java)
        startActivity(intent)
    }
}