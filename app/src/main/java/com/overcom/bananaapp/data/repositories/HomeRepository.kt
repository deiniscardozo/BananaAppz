package com.overcom.bananaapp.data.repositories

import com.overcom.bananaapp.data.network.service.HomeService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeService
) {

    constructor() : this(HomeService())

    suspend fun orgSelector() = api.getOrg()

    suspend fun orgErrorSelector() = api.getErrorOrg()

    suspend fun logout() = api.logout()
}