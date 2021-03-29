package com.mzakialkhairi.manga.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mzakialkhairi.manga.R
import com.mzakialkhairi.manga.databinding.ItemChapterLayoutBinding
import com.mzakialkhairi.manga.listeners.OnChapterClicked
import com.mzakialkhairi.manga.model.Chapter
import kotlinx.android.synthetic.main.item_chapter_layout.view.*

class ListChapterAdapter(private val list : ArrayList<Chapter>) : RecyclerView.Adapter<ListChapterAdapter.ViewHolder>() {

    var listener : OnChapterClicked ? = null

    inner class ViewHolder (itemView : ItemChapterLayoutBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_chapter_layout,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            itemChapter_title.text = list[position].chapter_title
            itemChapter_cv.setOnClickListener {
                listener?.chapterClicked(it,list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun addList(list: List<Chapter>){
        this.list.apply {
            clear()
            addAll(list)
        }
    }
}