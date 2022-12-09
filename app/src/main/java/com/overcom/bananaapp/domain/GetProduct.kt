package com.overcom.bananaapp.domain

import android.util.Log
import com.overcom.bananaapp.data.database.entities.toDatabase
import com.overcom.bananaapp.data.repositories.ProductsRepository
import com.overcom.bananaapp.domain.model.Products
import javax.inject.Inject

class GetProduct @Inject constructor(
    private val repository: ProductsRepository
) {

    constructor() : this(ProductsRepository())

    suspend operator fun invoke(): List<Products>? {
        val products = repository.getAllProductsFromApi()

        Log.i("Deinisrepo", repository.getAllProductsFromDatabase().toString())

        return if (products.isNotEmpty()) {
            repository.clearProducts(products.map { it.toDatabase() })
            repository.insertProducts(products.map { it.toDatabase() })
            Log.i("Deinisproducts", repository.getAllProductsFromDatabase().toString())
            Log.i("Deinisinsert", repository.insertProducts(products.map { it.toDatabase() }).toString())
            products

        } else {
            repository.getAllProductsFromDatabase()
        }
    }
}