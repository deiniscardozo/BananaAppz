package com.overcom.bananaapp.data.network.service

import com.overcom.bananaapp.core.ApiAdapter
import com.overcom.bananaapp.data.model.ThirdsData
import com.overcom.bananaapp.data.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

class ThirdsService @Inject constructor() {

    private val apiClient: ApiClient = ApiAdapter.getApiAdapter().create(ApiClient::class.java)

    suspend fun getThirds(
        type_third: String,
        filter: String,
        limit: String,
        position: String
    ): List<ThirdsData> {
        return withContext(Dispatchers.IO) {
            val response =
                apiClient?.listThirds(type_third, filter, limit, position)?.body()?.thirds
            response ?: emptyList()
        }
    }

    suspend fun getErrorThirds(
        type_third: String,
        filter: String,
        limit: String,
        position: String
    ): ResponseBody? {
        return withContext(Dispatchers.IO) {
            val response = apiClient?.listThirds(type_third, filter, limit, position)?.errorBody()
            response
        }
    }

}