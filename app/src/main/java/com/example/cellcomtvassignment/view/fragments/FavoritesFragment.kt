package com.example.cellcomtvassignment.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cellcomtvassignment.databinding.FragmentFavoritesBinding
import com.example.cellcomtvassignment.view.activities.MoviesMainActivity
import com.example.cellcomtvassignment.view.adapters.MoviesAdapter
import com.example.cellcomtvassignment.view.adapters.OnMovieClickedListener
import com.example.cellcomtvassignment.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {
    private lateinit var mFavoritesFragmentBinding: FragmentFavoritesBinding
    private lateinit var mFavoritesViewModel: FavoritesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MoviesMainActivity).isSignInFragment = false
        mFavoritesFragmentBinding = FragmentFavoritesBinding.inflate(inflater, null, false)
        return mFavoritesFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFavoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        val moviesAdapter = context?.let {
            MoviesAdapter(
                activity as OnMovieClickedListener,
                it
            )
        }
        mFavoritesViewModel.init()
        mFavoritesFragmentBinding.favoritesRecyclerView.adapter = moviesAdapter
        mFavoritesFragmentBinding.favoritesRecyclerView.layoutManager = GridLayoutManager(context, 3)

        mFavoritesViewModel.getFavoriteMovieList().observe(viewLifecycleOwner, { t ->
            moviesAdapter?.setMoviesList(t)
        })
    }

    companion object
}