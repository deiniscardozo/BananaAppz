package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.contact.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.data.model.ThirdContact
import com.overcom.bananaapp.databinding.ItemContacBinding

class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemContacBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(contactModel: ThirdContact) {

        binding.tvContacsName.text = contactModel.name
        binding.tvContacsCargo.text = contactModel.charge_name
        binding.tvContacsEmail.text = contactModel.email
        binding.tvContacsPhone.text = contactModel.phone.toString()
        binding.tvContacsPhone2.text = contactModel.phone_2.toString()

    }
}