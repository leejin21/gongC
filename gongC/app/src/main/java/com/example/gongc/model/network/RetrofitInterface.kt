package com.example.gongc.model.network

import com.example.gongc.model.dataclass.AuthData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitInterface {
    @POST("auth/login")
    fun postAuthLogin(@Body params:HashMap<String, String>): Call<AuthData>
    @POST("auth/signup")
    fun postAuthSignup(@Body params:HashMap<String, String>): Call<AuthData>
}