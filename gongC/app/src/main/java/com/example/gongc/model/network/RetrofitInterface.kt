package com.example.gongc.model.network

import com.example.gongc.model.dataclass.AuthData
import com.example.gongc.model.dataclass.ConcentDailyData
import com.example.gongc.model.dataclass.ConcentWeeklyData
import com.example.gongc.model.dataclass.HomeNicknameData
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @POST("auth/login")
    fun postAuthLogin(@Body params:HashMap<String, String>): Call<AuthData>
    @POST("auth/signup")
    fun postAuthSignup(@Body params:HashMap<String, String>): Call<AuthData>

    @GET("concent/daily_data")
    fun getConcentDailydata(@Header("x-access-token") token:String): Call<ConcentDailyData>

    @GET("concent/weekly_data")
    fun getConcentWeeklydata(@Header("x-access-token") token:String): Call<ConcentWeeklyData>

    @GET("home/nickname")
    fun getHomeNicknamedata(@Header("x-access-token") token:String): Call<HomeNicknameData>
}