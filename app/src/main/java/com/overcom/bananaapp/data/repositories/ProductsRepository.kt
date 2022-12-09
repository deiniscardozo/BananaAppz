package com.overcom.bananaapp.data.repositories

import android.util.Log
import com.overcom.bananaapp.data.database.dao.ProductsDao
import com.overcom.bananaapp.data.database.entities.ProductsEntity
import com.overcom.bananaapp.data.model.ProductsModel
import com.overcom.bananaapp.data.network.service.ProductsService
import com.overcom.bananaapp.domain.model.Products
import com.overcom.bananaapp.domain.model.toDomain
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val api: ProductsService
) {

    constructor() : this(ProductsService())

    private val productsDao: ProductsDao? = null

    suspend fun getAllProductsFromApi(): List<Products> {
        val response: List<ProductsModel>? = api.getProducts()?.products
        Log.i("DeinisselectAPI", api.getProducts()?.products.toString())
        return response!!.map { it.toDomain() }

    }

    suspend fun getAllProductsFromDatabase(): List<Products>? {
        val response: List<ProductsEntity>? = productsDao?.getAll()
        Log.i("DeinisselectDB", response.toString())
        return response?.map { it.toDomain() }
    }

    suspend fun insertProducts(products: List<ProductsEntity>) {
        productsDao?.insertAll(products)
    }

    suspend fun clearProducts(products: List<ProductsEntity>) {
        productsDao?.deleteAll(products)
    }
}