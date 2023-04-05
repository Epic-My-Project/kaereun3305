package com.kaereun3305.a20230303kt

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetinfoService {
    @FormUrlEncoded
    @POST("https://a4aa-117-16-195-25.jp.ngrok.io/userinfo/")

    fun requestNetInfo(
        @Field("n_id") netid:String,
        @Field("n_pw") netpw:String,
    ) : Call<netinfo> //아웃풋 정의
}