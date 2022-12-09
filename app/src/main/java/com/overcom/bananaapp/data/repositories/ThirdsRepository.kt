package com.overcom.bananaapp.data.repositories

import com.overcom.bananaapp.data.network.service.ThirdsService
import javax.inject.Inject

class ThirdsRepository @Inject constructor(
    private val api: ThirdsService
) {

    constructor() : this(ThirdsService())

    suspend fun thirdsCall(type_third: String, filter: String, limit: String, position: String) =
        api.getThirds(type_third, filter, limit, position)

    suspend fun thirdsErrorCall(
        type_third: String,
        filter: String,
        limit: String,
        position: String
    ) = api.getErrorThirds(type_third, filter, limit, position)
}