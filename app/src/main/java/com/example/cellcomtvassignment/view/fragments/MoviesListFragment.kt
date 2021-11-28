package com.example.cellcomtvassignment.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cellcomtvassignment.databinding.FragmentMoviesListBinding
import com.example.cellcomtvassignment.view.adapters.MoviesAdapter
import com.example.cellcomtvassignment.view.adapters.OnMovieClickedListener
import com.example.cellcomtvassignment.viewmodel.MoviesListViewModel

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton





class MoviesListFragment : Fragment() {
    private lateinit var mMoviesListFragmentBinding: FragmentMoviesListBinding
    private var category : String = POPULAR
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mMoviesListFragmentBinding = FragmentMoviesListBinding.inflate(inflater, null, false)
        return mMoviesListFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mMoviesListViewModel: MoviesListViewModel =
            ViewModelProvider(this)[MoviesListViewModel::class.java]
        mMoviesListViewModel.init()
        val moviesRecyclerView = mMoviesListFragmentBinding.moviesRecyclerView
        val moviesAdapter = context?.let {
            MoviesAdapter(
                activity as OnMovieClickedListener,
                it
            )
        }
        moviesRecyclerView.adapter = moviesAdapter
        moviesRecyclerView.layoutManager = GridLayoutManager(context, 3)
        mMoviesListViewModel.isDataChanged().observe(viewLifecycleOwner, { result ->
            if (result == true)
                mMoviesListViewModel.mMoviesList.value?.let { moviesAdapter?.setStatusesList(it) }
        })
        mMoviesListFragmentBinding.toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            val button: MaterialButton = mMoviesListFragmentBinding.toggleButtonGroup.findViewById(checkedId)
            if (isChecked){
            } else {
                //Something is unchecked, we need to make sure that all the buttons are not un-selected
                if(-1 == group.checkedButtonId){
                    //All buttons are unselected
                    //So now we will select the button which was unselected right now
                    group.check(checkedId)
                }
            }
            button.setOnClickListener {
                val newCategory = button.text.toString()
                if(category != newCategory) {
                    moviesAdapter?.clear()
                    category = newCategory
                    mMoviesListViewModel.getData(category)
                }
            }
        }
        moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mMoviesListViewModel.nextPage(category)
                }
            }
        })

    }

    companion object
    {
        const val POPULAR="Popular"
    }
}