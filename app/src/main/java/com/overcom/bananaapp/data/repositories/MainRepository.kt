package com.overcom.bananaapp.data.repositories

import com.overcom.bananaapp.core.ApiAdapter
import com.overcom.bananaapp.data.model.Workspace
import com.overcom.bananaapp.data.network.ApiClient

class MainRepository {

    private val apiService: ApiClient = ApiAdapter.getApiAdapter().create(ApiClient::class.java)

    suspend fun callServiceGetUsers(email: String, password: String) =
        apiService.listUsers(email, password.trim())

    suspend fun validateWorkspace(workspace: String) =
        apiService.validateWorkspace(Workspace(workspace))

    suspend fun forgetPassword(email: String) =
        apiService.forgetPassword(email)
}