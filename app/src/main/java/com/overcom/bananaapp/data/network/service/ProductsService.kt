package com.overcom.bananaapp.data.network.service

import android.util.Log
import com.overcom.bananaapp.core.ApiAdapter
import com.overcom.bananaapp.data.model.ProductsDataItem
import com.overcom.bananaapp.data.model.ProductsModel
import com.overcom.bananaapp.data.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsService @Inject constructor() {

    private val apiClient: ApiClient = ApiAdapter.getApiAdapter().create(ApiClient::class.java)

    suspend fun getProducts(): ProductsDataItem? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.listProducts()?.body()
            response
        }
    }
}