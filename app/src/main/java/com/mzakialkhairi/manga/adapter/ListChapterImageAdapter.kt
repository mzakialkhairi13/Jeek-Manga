package com.mzakialkhairi.manga.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.ItemChapterImageLayoutBinding
import com.mzakialkhairi.manga.model.ChapterImage
import com.mzakialkhairi.manga.model.Komik
import kotlinx.android.synthetic.main.item_chapter_image_layout.view.*

class ListChapterImageAdapter(private val list : ArrayList<ChapterImage>) : RecyclerView.Adapter<ListChapterImageAdapter.ViewHolder>(){

    inner class ViewHolder (itemView : ItemChapterImageLayoutBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.item_chapter_image_layout , parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(context).load(list[position].chapter_image_link).into(chapterImage_iv)
        }
    }

    override fun getItemCount(): Int = list.size

    fun addList( list: List<ChapterImage>){
        this.list.apply {
            clear()
            addAll(list)
        }
    }

}