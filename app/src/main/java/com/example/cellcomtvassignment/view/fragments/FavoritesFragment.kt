package com.example.cellcomtvassignment.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cellcomtvassignment.R
import com.example.cellcomtvassignment.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var mFavoritesFragmentBinding: FragmentFavoritesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFavoritesFragmentBinding = FragmentFavoritesBinding.inflate(inflater, null, false)
        return mFavoritesFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object
}