package com.kaereun3305.a20230303kt

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetinfoService {
    @FormUrlEncoded
    @POST("https://ce17-117-16-244-19.jp.ngrok.io/usersignup/")

    fun requestNetInfo(
        @Field("netflixid") netid:String,
        @Field("netfiixpw") netpw:String,
    ) : Call<netinfo> //아웃풋 정의
}