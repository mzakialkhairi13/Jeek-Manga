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
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.adapter.ListKomikPopulerAdapter
import com.mzakialkhairi.manga.databinding.ListKomikFragmentBinding
import com.mzakialkhairi.manga.model.Komik
import com.mzakialkhairi.manga.model.States
import com.mzakialkhairi.manga.ui.detail_komik.DetailKomikActivity
import com.mzakialkhairi.manga.utils.interfaces.OnKomikClicked


class ListKomikFragment : Fragment(), OnKomikClicked {


    private lateinit var viewModel: ListKomikViewModel
    private lateinit var binding : ListKomikFragmentBinding
    private var page : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate( inflater,R.layout.list_komik_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(ListKomikViewModel::class.java)

        val rv = binding.listmangaRvPopuler
        rv.layoutManager = LinearLayoutManager(this.context)
        rv.setHasFixedSize(true)

        viewModel.getStates().observe(viewLifecycleOwner, Observer { handleState(it) })

        getListPropularViewModel(page)

        binding.next.setOnClickListener {
            page ++
            getListPropularViewModel(page)
        }

        return binding.root
    }

    private fun showToast(msg : String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
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


    fun getListPropularViewModel(page :Int){
        viewModel.getListPopular(page)
        viewModel.listPopular.observe(viewLifecycleOwner, Observer {
            val rv = binding.listmangaRvPopuler
            val adapter = ListKomikPopulerAdapter(it)
            rv.adapter = adapter
            adapter.listener = this
            showLoading(false)
        })


    }

    private fun showLoading (state : Boolean){
        if (state){
            binding.listMangaContainer.visibility = View.GONE
            binding.detailKomikProgressBar.visibility = View.VISIBLE
            binding.next.text = "..cotto matte.."
        }
        else{
            binding.listMangaContainer.visibility = View.VISIBLE
            binding.detailKomikProgressBar.visibility = View.GONE
            binding.next.text = "Selanjutnya ke " +(page+1).toString()
        }
    }

}
