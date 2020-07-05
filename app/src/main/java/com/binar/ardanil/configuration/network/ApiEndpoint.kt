package com.binar.ardanil.configuration.network

import com.binar.ardanil.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {

    @GET(ApiKeys.USERS)
    fun getUsers(
        @Query("per_page") per_page: String?
    ): Call<UserResponse>

}