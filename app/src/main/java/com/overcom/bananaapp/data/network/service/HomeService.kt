package com.overcom.bananaapp.data.network.service

import com.overcom.bananaapp.core.ApiAdapter
import com.overcom.bananaapp.data.model.Logout
import com.overcom.bananaapp.data.model.OrganizationsData
import com.overcom.bananaapp.data.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

class HomeService @Inject constructor() {

    private val apiClient: ApiClient = ApiAdapter.getApiAdapter().create(ApiClient::class.java)

    suspend fun getOrg(): OrganizationsData? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.listOrganizations()?.body()
            response
        }
    }

    suspend fun getErrorOrg(): ResponseBody? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.listOrganizations()?.errorBody()
            response
        }
    }

    suspend fun logout(): Logout? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.logout().body()
            response
        }
    }
}