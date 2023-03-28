package com.kaereun3305.a20230303kt

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("https://9d4f-117-16-195-14.jp.ngrok.io/userlogin/")

    fun requestLogin(
        @Field("userid") userid:String,
        @Field("userpw") userpw:String
    ) : Call<Login> //아웃풋 정의
}