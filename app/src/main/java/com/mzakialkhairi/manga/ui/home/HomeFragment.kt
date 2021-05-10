package com.mzakialkhairi.manga.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser

import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.adapter.ListRecommendedAdapter
import com.mzakialkhairi.manga.databinding.HomeFragmentBinding
import com.mzakialkhairi.manga.listeners.OnMangaClicked
import com.mzakialkhairi.manga.model.Manga
import com.mzakialkhairi.manga.model.States
import com.mzakialkhairi.manga.source.local.ListBanner
import com.mzakialkhairi.manga.ui.detail_komik.DetailKomikActivity
import com.mzakialkhairi.manga.ui.search_komik.SearchActivity

class HomeFragment : Fragment(), OnMangaClicked {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel : HomeViewModel
    private lateinit var binding : HomeFragmentBinding
    private lateinit var adapter : ListRecommendedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate( inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        setupCorouselPromotion()
        setupUI()
        setupViewModel()

        binding.homeCariKomik.setOnClickListener {
            startActivity(Intent(context,SearchActivity::class.java))
        }

        return binding.root
    }

    private fun setupUI(){
        val rv = binding.rvRecommended
        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        adapter = ListRecommendedAdapter(arrayListOf())
        rv.adapter = adapter
        adapter.listener = this
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getStates().observer(viewLifecycleOwner, { handleState(it) })
        viewModel.getRecommended()
        viewModel.listRecommended.observe(viewLifecycleOwner,{
            if (it.isNotEmpty()){
                showRecommended(it)
            }
        })
    }

    private fun showRecommended(list: List<Manga>){
        adapter.apply {
            addList(list)
            notifyDataSetChanged()
        }
    }

    private fun setupCorouselPromotion( ) {
        val carouselPromosi = binding.homeCarousel
        val list = ListBanner.getUriBanner()
        carouselPromosi.setImageListener { position, imageView ->
            Glide.with(this).load(list[position]).into(imageView)
        }
        carouselPromosi.pageCount = list.size
    }

    private fun handleState(it: States){
        when(it){
            is States.error -> {
//                showLoading(false)
//                showToast(it.error)
            }
            is States.isLoading-> {
                if (it.state == true){
//                    showLoading(true)
                }
                else {
//                    showLoading(false)
                }
            }
        }
    }

    override fun mangaClicked(view: View, manga: Manga) {
        val intent = Intent(this.context, DetailKomikActivity::class.java)
        intent.putExtra(DetailKomikActivity.endPoint,manga.endpoint)
        startActivity(intent)
    }


}
