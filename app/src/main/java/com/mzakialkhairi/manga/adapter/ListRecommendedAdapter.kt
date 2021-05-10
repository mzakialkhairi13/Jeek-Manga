package com.mzakialkhairi.manga.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.ItemRecomenderKomikLayoutBinding
import com.mzakialkhairi.manga.listeners.OnMangaClicked
import com.mzakialkhairi.manga.model.Manga
import kotlinx.android.synthetic.main.item_recomender_komik_layout.view.*


class ListRecommendedAdapter(private val list : ArrayList<Manga>) : RecyclerView.Adapter<ListRecommendedAdapter.ViewHolder>() {

    var listener : OnMangaClicked? = null

    inner class ViewHolder (itemView : ItemRecomenderKomikLayoutBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_recomender_komik_layout,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.apply {
            recommended_title.text = item.title
            Glide.with(context).load(item.thumb).into(recommended_picture)
            recommended_cv.setOnClickListener {
                listener?.mangaClicked(it,item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun addList(list: List<Manga>){
        this.list.apply {
            clear()
            addAll(list)
        }
    }
}