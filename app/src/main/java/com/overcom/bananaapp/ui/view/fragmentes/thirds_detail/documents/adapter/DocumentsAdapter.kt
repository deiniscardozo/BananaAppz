package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.documents.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.R
import com.overcom.bananaapp.data.model.DocumentsItem

class DocumentsAdapter(private val listDocuments: List<DocumentsItem>) :
    RecyclerView.Adapter<DocumentsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DocumentsViewHolder(layoutInflater.inflate(
            R.layout.item_documents, parent
            , false))
    }

    override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
        val item = listDocuments[position]
        holder.render(item)
    }
    override fun getItemCount(): Int = listDocuments.size

}