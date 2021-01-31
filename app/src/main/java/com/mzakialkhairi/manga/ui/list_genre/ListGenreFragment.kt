package com.mzakialkhairi.manga.ui.list_genre

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mzakialkhairi.manga.R

class ListGenreFragment : Fragment() {

    companion object {
        fun newInstance() = ListGenreFragment()
    }

    private lateinit var viewModel: ListGenreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_genre_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListGenreViewModel::class.java)
        // TODO: Use the ViewModel
    }

}