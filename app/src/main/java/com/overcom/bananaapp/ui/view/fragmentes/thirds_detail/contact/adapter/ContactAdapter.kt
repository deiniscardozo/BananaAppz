package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.R
import com.overcom.bananaapp.data.model.ThirdContact

class ContactAdapter(private val listContact: List<ThirdContact>) :
    RecyclerView.Adapter<ContactViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactViewHolder(layoutInflater.inflate(
            R.layout.item_contac, parent
            , false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = listContact[position]
        holder.render(item)

    }
    override fun getItemCount(): Int = listContact.size

}