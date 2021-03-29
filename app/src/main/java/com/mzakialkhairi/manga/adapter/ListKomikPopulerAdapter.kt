package com.mzakialkhairi.manga.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.ItemListKomikLayoutBinding
import com.mzakialkhairi.manga.model.Komik
import com.mzakialkhairi.manga.listeners.OnKomikClicked
import kotlinx.android.synthetic.main.item_list_komik_layout.view.*

class ListKomikPopulerAdapter(private val list : ArrayList<Komik>) : RecyclerView.Adapter<ListKomikPopulerAdapter.ViewHolder>(){

    var listener : OnKomikClicked? = null

    inner class ViewHolder (itemView : ItemListKomikLayoutBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.item_list_komik_layout , parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(context).load(list[position].thumb).into(itemPopular_ivFoto)
            itemPopular_tvName.text = list[position].title
            itemPopular_tvKategori.text = list[position].type
            itemPopular_tvUpdate.text = list[position].upload_on
        }

        holder.itemView.itemPopular_container.setOnClickListener {
            listener?.onKomikIsCLicked(it,list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    fun addList( list: List<Komik>){
        this.list.apply {
            clear()
            addAll(list)
        }
    }

}