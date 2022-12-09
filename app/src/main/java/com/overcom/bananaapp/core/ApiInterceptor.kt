package com.overcom.bananaapp.core

import com.overcom.bananaapp.BananaApp.Companion.preferences
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val workspace: String = preferences.getWorkspace().ifEmpty { "false" }
        val email: String = preferences.getEmail().ifEmpty { "false" }
        val token: String = preferences.getToken().ifEmpty { "false" }
        val userid: String = preferences.getUserID().ifEmpty { "false" }
        val client: String = preferences.getClient().ifEmpty { "false" }
        val orgId: String = preferences.getOrgID().ifEmpty { "false" }

        val request = chain.request().newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("app", "BananaApp")
            .addHeader("authorization", "https://$workspace.bananaerp.com")
            .addHeader("encrypted", "true")
            .addHeader("token", token)
            .addHeader("user", userid)
            .addHeader("workspace", workspace)
            .addHeader("client", client)
            .addHeader("email", email)
            .addHeader("organization", orgId).build()

        return chain.proceed(request)
    }
}