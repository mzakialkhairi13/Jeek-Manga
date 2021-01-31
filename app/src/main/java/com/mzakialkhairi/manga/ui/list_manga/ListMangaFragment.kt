package com.mzakialkhairi.manga.ui.list_manga

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mzakialkhairi.manga.R

class ListMangaFragment : Fragment() {

    companion object {
        fun newInstance() = ListMangaFragment()
    }

    private lateinit var viewModel: ListMangaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_manga_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListMangaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
