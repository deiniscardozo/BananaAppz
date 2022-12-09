package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.archivos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.R
import com.overcom.bananaapp.data.model.Archivo

class ArchivosAdapter (private val listArchivo: List<Archivo>) :
    RecyclerView.Adapter<ArchivosViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ArchivosViewHolder(layoutInflater.inflate(
            R.layout.item_archivos, parent
            , false))
    }

    override fun onBindViewHolder(holder: ArchivosViewHolder, position: Int) {
        val item = listArchivo[position]
        holder.render(item)

    }
    override fun getItemCount(): Int = listArchivo.size

}