package com.mzakialkhairi.manga.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.ItemListMangaLayoutBinding
import com.mzakialkhairi.manga.model.Manga
import kotlinx.android.synthetic.main.item_list_manga_layout.view.*

class ListMangaPopulerAdapter(private val list : ArrayList<Manga>) : RecyclerView.Adapter<ListMangaPopulerAdapter.ViewHolder>(){

    inner class ViewHolder (itemView : ItemListMangaLayoutBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.item_list_manga_layout , parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(context).load(list[position].thumb).into(itemPopular_ivFoto)
            itemPopular_tvName.text = list[position].title
            itemPopular_tvKategori.text = list[position].type
            itemPopular_tvUpdate.text = list[position].upload_on
        }
    }

    override fun getItemCount(): Int = list.size

}