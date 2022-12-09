package com.overcom.bananaapp.data.repositories

import com.overcom.bananaapp.core.ApiAdapter
import com.overcom.bananaapp.data.network.ApiClient

class ThirdsDetailRepository {

    private val apiService: ApiClient = ApiAdapter.getApiAdapter().create(ApiClient::class.java)

    suspend fun callThirdsDetail(id: String) =
        apiService.listThirdsDetail(id, "1")
}