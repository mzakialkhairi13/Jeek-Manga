package com.mzakialkhairi.manga.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide

import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.HomeFragmentBinding
import com.mzakialkhairi.manga.source.local.ListBanner
import com.mzakialkhairi.manga.ui.search_komik.SearchActivity

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel : HomeViewModel
    private lateinit var binding : HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate( inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        setupCorouselPromotion()

        binding.homeCariKomik.setOnClickListener {
            startActivity(Intent(context,SearchActivity::class.java))
        }

        return binding.root
    }

    private fun setupCorouselPromotion( ) {
        val carouselPromosi = binding.homeCarousel
        val list = ListBanner.getUriBanner()
        carouselPromosi.setImageListener { position, imageView ->
            Glide.with(this).load(list[position]).into(imageView)
        }
        carouselPromosi.pageCount = list.size
    }

}
