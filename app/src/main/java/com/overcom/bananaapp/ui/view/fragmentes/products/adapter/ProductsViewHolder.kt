package com.overcom.bananaapp.ui.view.fragmentes.products.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.overcom.bananaapp.R
import com.overcom.bananaapp.databinding.ItemProductsBinding
import com.overcom.bananaapp.domain.model.Products
import java.math.RoundingMode


class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemProductsBinding.bind(view)

    fun render(products: Products, onClickListener: (Products) -> Unit) {

        Glide.with(binding.ivAvatar.context).load(products.image)
            .placeholder(R.drawable.ic_thirds).into(binding.ivAvatar)
        binding.productsName.text = products.name
        binding.productsEan13.text = products.ean13
        // binding.productDate.text = products
        binding.productReferencia.text = products.reference
        binding.productStock.text = products.stock
        binding.productSku.text = products.sku
        binding.productPrice.text =
            products.netprice?.toBigDecimal()?.setScale(2, RoundingMode.UP).toString()

        /* if (thirdsModel.archived == 1) {
             binding.itemThirds.setBackgroundResource(R.drawable.bg_recycler_ic_ar)
         } else {
             binding.itemThirds.setBackgroundResource(R.drawable.bg_recycler_ic)
         }*/

        itemView.setOnClickListener { onClickListener(products) }

    }
}