package com.mzakialkhairi.manga.ui.list_komik

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.adapter.ListKomikPopulerAdapter
import com.mzakialkhairi.manga.databinding.ListKomikFragmentBinding
import com.mzakialkhairi.manga.model.Komik
import com.mzakialkhairi.manga.model.States
import com.mzakialkhairi.manga.ui.detail_komik.DetailKomikActivity
import com.mzakialkhairi.manga.listeners.OnKomikClicked


class ListKomikFragment : Fragment(), OnKomikClicked {

    private lateinit var viewModel: ListKomikViewModel
    private lateinit var binding : ListKomikFragmentBinding
    private lateinit var adapter : ListKomikPopulerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate( inflater,R.layout.list_komik_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(ListKomikViewModel::class.java)

        buttonHandle()
        setupUI()

        viewModel.getStates().observe(viewLifecycleOwner, { handleState(it) })
        getListPropularViewModel(viewModel.page)


        return binding.root
    }

    private fun showToast(msg : String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    private fun setupUI(){
        val rv = binding.listmangaRvPopuler
        rv.layoutManager = LinearLayoutManager(this.context)
        rv.setHasFixedSize(true)
        rv.addItemDecoration(
            DividerItemDecoration(
                rv.context,
                (rv.layoutManager as LinearLayoutManager).orientation
            )
        )
        adapter = ListKomikPopulerAdapter(arrayListOf())
        rv.adapter = adapter
        adapter.listener = this


        binding.next.setOnClickListener {
            viewModel.page++
            getListPropularViewModel(viewModel.page)
            buttonHandle()
        }

        binding.prev.setOnClickListener {
            viewModel.page--
            getListPropularViewModel(viewModel.page)
            buttonHandle()
        }

    }

    override fun onKomikIsCLicked(view: View, komik: Komik) {
        val intent = Intent(this.context,DetailKomikActivity::class.java)
        intent.putExtra(DetailKomikActivity.endPoint,komik.endpoint)
        startActivity(intent)
    }

    private fun handleState(it: States){
        when(it){
            is States.error -> {
                showLoading(false)
                showToast(it.error)
            }
            is States.isLoading-> {
                if (it.state == true){
                    showLoading(true)
                }
                else {
                    showLoading(false)
                }
            }
        }
    }

    private fun buttonHandle(){
        if (viewModel.page == 1){
            binding.prev.visibility = View.GONE
        }
        else {
            binding.prev.visibility = View.VISIBLE
        }
    }


    fun getListPropularViewModel(page :Int){
        viewModel.getListPopular(page)
        viewModel.listPopular.observe(viewLifecycleOwner,  {
            showListKomik(it)
            showLoading(false)
        })
    }

    private fun showListKomik(list : List<Komik>){
        adapter.apply {
          addList(list)
          notifyDataSetChanged()
        }
    }

    private fun showLoading (state : Boolean){
        if (state){
            binding.detailKomikProgressBar.visibility = View.VISIBLE
            binding.listMangaContainer.visibility = View.GONE
            binding.prev.isEnabled = false
            binding.next.isEnabled = false
        }
        else{
            binding.detailKomikProgressBar.visibility = View.GONE
            binding.listMangaContainer.visibility = View.VISIBLE
            binding.prev.isEnabled = true
            binding.next.isEnabled = true
        }
    }

}
