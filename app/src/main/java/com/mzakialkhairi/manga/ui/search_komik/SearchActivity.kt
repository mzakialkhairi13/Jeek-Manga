package com.mzakialkhairi.manga.ui.search_komik

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.adapter.ListKomikPopulerAdapter
import com.mzakialkhairi.manga.databinding.ActivitySearchBinding
import com.mzakialkhairi.manga.listeners.OnKomikClicked
import com.mzakialkhairi.manga.model.Komik
import com.mzakialkhairi.manga.ui.detail_komik.DetailKomikActivity
import com.mzakialkhairi.manga.ui.list_komik.ListKomikViewModel

class SearchActivity : AppCompatActivity(), OnKomikClicked {

    private lateinit var binding : ActivitySearchBinding
    private lateinit var adapter: ListKomikPopulerAdapter
    private lateinit var viewModel : ListKomikViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)

        viewModel = ViewModelProviders.of(this).get(ListKomikViewModel::class.java)

        val rv = binding.searchRvKomik
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(
            DividerItemDecoration(
                rv.context,
                (rv.layoutManager as LinearLayoutManager).orientation
            )
        )
        adapter = ListKomikPopulerAdapter(arrayListOf())
        rv.adapter = adapter
        adapter.listener = this

        val searchView = binding.searchEtKeyword
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Cari komik..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if  (newText.isNotEmpty()){
                    viewModel.getSearchKomik(newText)
                }
                return false
            }
        })

        viewModel.searchKomik.observe(this,{
            showListKomik(it.manga_list)
        })

    }

    private fun showListKomik(list: List<Komik>){
        adapter.apply {
            addList(list)
            notifyDataSetChanged()
        }
    }

    override fun onKomikIsCLicked(view: View, komik: Komik) {
        val intent = Intent(this,DetailKomikActivity::class.java)
        intent.putExtra(DetailKomikActivity.endPoint , komik.endpoint)
        startActivity(intent)
    }


}