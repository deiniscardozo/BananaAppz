package com.overcom.bananaapp.ui.view.fragmentes.thirds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.R
import com.overcom.bananaapp.data.model.ThirdsData

class ThirdsAdapter(private var listThirds: MutableList<ThirdsData>,
                    private val onClickListener:(ThirdsData) -> Unit) :
    RecyclerView.Adapter<ThirdsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ThirdsViewHolder(layoutInflater.inflate(
            R.layout.item_thirds, parent
            , false))
    }

    override fun onBindViewHolder(holder: ThirdsViewHolder, position: Int) {
        val item = listThirds[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = listThirds.size

    fun setItems(list: List<ThirdsData>) {
        listThirds.clear()
        listThirds.addAll(list)
        notifyDataSetChanged()
    }
}