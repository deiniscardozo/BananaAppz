package com.overcom.bananaapp.ui.view.fragmentes.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.R
import com.overcom.bananaapp.domain.model.Products

class ProductsAdapter(
    private val listProducts: MutableList<Products>,
    private val onClickListener: (Products) -> Unit
) :
    RecyclerView.Adapter<ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(
            layoutInflater.inflate(
                R.layout.item_products, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = listProducts[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = listProducts.size

    fun setItems(list: List<Products>) {
        listProducts.clear()
        listProducts.addAll(list)
        notifyDataSetChanged()
    }
}