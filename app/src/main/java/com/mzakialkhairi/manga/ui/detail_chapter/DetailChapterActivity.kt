package com.mzakialkhairi.manga.ui.detail_chapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.adapter.ListChapterImageAdapter
import com.mzakialkhairi.manga.databinding.ActivityDetailChapterBinding
import com.mzakialkhairi.manga.model.ChapterImage
import com.mzakialkhairi.manga.model.States

class DetailChapterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailChapterBinding
    private lateinit var viewModel: DetailChapterViewModel
    private lateinit var adapter: ListChapterImageAdapter

    companion object{
        const val end_point = "end_point"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_chapter)
        viewModel = ViewModelProviders.of(this).get(DetailChapterViewModel::class.java)

        val rv = binding.detailChapterRvKomik
        rv.layoutManager = LinearLayoutManager(this)
        adapter = ListChapterImageAdapter(arrayListOf())
        rv.adapter = adapter

        viewModel.getState().observer(this, { handleState(it) })
        val endPoint = intent.getStringExtra(end_point)
        viewModel.getChapterImage(endPoint!!)
        viewModel.dataChapter.observe(this, {
            showList(it.chapter_image)
        })
    }

    private fun showList(list : List<ChapterImage>){
        adapter.apply {
            addList(list)
            notifyDataSetChanged()
        }
    }

    private fun handleState(it: States){
        when(it){
            is States.error -> {
                showToast(it.error)
            }
//            is States.isLoading-> {
//                if (it.state == true){
//                    showLoading(true)
//                }
//                else {
//                    showLoading(false)
//                }
//            }
        }
    }
    private fun showToast(msg : String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

}