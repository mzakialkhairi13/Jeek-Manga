package com.mzakialkhairi.manga.ui.list_manga

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.adapter.ListMangaPopulerAdapter
import com.mzakialkhairi.manga.databinding.ListMangaFragmentBinding

class ListMangaFragment : Fragment() {


    private lateinit var viewModel: ListMangaViewModel
    private lateinit var binding : ListMangaFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate( inflater,R.layout.list_manga_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(ListMangaViewModel::class.java)
        val rv = binding.listmangaRvPopuler
        rv.layoutManager = LinearLayoutManager(this.context)
        rv.setHasFixedSize(true)
        viewModel.listPopular.observe(viewLifecycleOwner, Observer {
            rv.adapter = ListMangaPopulerAdapter(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })

        return binding.root
    }

    private fun showToast(msg : String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

}
