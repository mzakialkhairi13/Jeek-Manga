package com.mzakialkhairi.manga.ui.detail_komik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.adapter.ListChapterAdapter
import com.mzakialkhairi.manga.databinding.ActivityDetailKomikBinding
import com.mzakialkhairi.manga.listeners.OnChapterClicked
import com.mzakialkhairi.manga.model.Chapter
import com.mzakialkhairi.manga.model.DetailKomikResponse
import com.mzakialkhairi.manga.model.Genre
import com.mzakialkhairi.manga.model.States
import com.mzakialkhairi.manga.ui.detail_chapter.DetailChapterActivity
import kotlinx.android.synthetic.main.activity_detail_komik.*

class DetailKomikActivity : AppCompatActivity(), OnChapterClicked {

    private lateinit var binding : ActivityDetailKomikBinding
    private lateinit var viewModel : DetailKomikViewModel
    private lateinit var adapter : ListChapterAdapter

    companion object {
        const val endPoint = "extra-end-point"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_komik)

        val endpoint = intent.getStringExtra(endPoint)

        showLoading(true)
        setupUI()

        viewModel = ViewModelProviders.of(this).get(DetailKomikViewModel::class.java)
        viewModel.getState().observer(this, Observer { handleState(it) })

        endpoint?.let {
            viewModel.getDataKomik(it)
        }
        viewModel.dataKomik.observe(this, Observer {
            handleUI(it)
            showLoading(false)})
    }

    private fun setupUI(){
        val rv = binding.container.detailKomikRvChapters
        rv.layoutManager = LinearLayoutManager(this)
        adapter = ListChapterAdapter(arrayListOf())
        rv.adapter = adapter
        adapter.listener = this

        binding.detailKomikBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun handleUI(data : DetailKomikResponse){
        binding.apply {
            detailKomikTvTitle.text = data.title
            detailKomikTvAuthor.text = data.author
            container.detailKomikTvSinopsis.text = data.synopsis
            detailKomikTvType.text = data.type

            val listGenre = ArrayList<String>()
            for (i in data.genre_list.indices){
                listGenre.add(data.genre_list[i].genre_name)
            }

            detailKomikTvGenre.text = listOf(listGenre).toString()

            if (data.status == ""){
                detailKomikTvStatus.text = "-"
            }else{
                detailKomikTvStatus.text = data.status
                if (data.status == "Ongoing"){
                    detailKomikTvStatus.setBackgroundResource(R.drawable.shape_green_rad4)
                }
            }
        }
        Glide.with(this).load(data.thumb).into(binding.detailKomikIvThumb)
        showListChapters(data.chapter)
    }

    private fun showListChapters(list : List<Chapter>){
        adapter.apply {
            addList(list)
            notifyDataSetChanged()
        }
    }

    private fun showLoading(state : Boolean){
        if (state){
            binding.apply {
                detailKomik_progressBar.visibility = View.VISIBLE
                detailKomik_container.visibility = View.GONE
            }
        }else{
            detailKomik_progressBar.visibility = View.GONE
            detailKomik_container.visibility = View.VISIBLE
        }
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

    private fun showToast(msg : String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    override fun chapterClicked(view: View, chapter: Chapter) {
        val intent = Intent(this,DetailChapterActivity::class.java)
        intent.putExtra(DetailChapterActivity.end_point,chapter.chapter_endpoint)
        startActivity(intent)

    }

}
