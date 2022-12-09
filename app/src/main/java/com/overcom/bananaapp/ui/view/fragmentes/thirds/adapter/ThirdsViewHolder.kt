package com.overcom.bananaapp.ui.view.fragmentes.thirds.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.overcom.bananaapp.R
import com.overcom.bananaapp.data.model.ThirdsData
import com.overcom.bananaapp.databinding.ItemThirdsBinding
import java.math.RoundingMode


class ThirdsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemThirdsBinding.bind(view)

    fun render(thirdsModel: ThirdsData, onClickListener:(ThirdsData) -> Unit) {

        Glide.with(binding.ivAvatar.context).load(thirdsModel.logo)
            .placeholder(R.drawable.ic_thirds).into(binding.ivAvatar)
        binding.tvThirdsName.text = thirdsModel.name
        binding.tvThirdsCif.text = thirdsModel.cif
        binding.tvThirdsBalance.text = thirdsModel.total_open_balance.toBigDecimal().
            setScale(2, RoundingMode.UP).toString()

        if (thirdsModel.archived == 1) {
            binding.itemThirds.setBackgroundResource(R.drawable.bg_recycler_ic_ar)
        } else {
            binding.itemThirds.setBackgroundResource(R.drawable.bg_recycler_ic)
        }

        itemView.setOnClickListener { onClickListener(thirdsModel) }

    }
}