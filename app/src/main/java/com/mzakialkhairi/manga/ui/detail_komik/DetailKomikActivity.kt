package com.mzakialkhairi.manga.ui.detail_komik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.ActivityDetailKomikBinding
import com.mzakialkhairi.manga.model.DetailKomikResponse
import com.mzakialkhairi.manga.model.Genre
import kotlinx.android.synthetic.main.activity_detail_komik.*

class DetailKomikActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailKomikBinding
    private lateinit var viewModel : DetailKomikViewModel

    companion object {
        const val endPoint = "extra-end-point"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_komik)

        val endpoint = intent.getStringExtra(endPoint)

        showLoading(true)

        viewModel = ViewModelProviders.of(this).get(DetailKomikViewModel::class.java)
        endpoint?.let {
            viewModel.getDataKomik(it)
        }
        viewModel.dataKomik.observe(this, Observer {
            handleUI(it)
            showLoading(false)})
    }

    private fun handleUI(data : DetailKomikResponse){
        binding.apply {
            detailKomikTvTitle.text = data.title
            detailKomikTvAuthor.text = data.author
            detailKomikTvSinopsis.text = data.synopsis
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

}
