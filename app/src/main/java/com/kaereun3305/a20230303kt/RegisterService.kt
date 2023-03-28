package com.kaereun3305.a20230303kt

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterService {

    @FormUrlEncoded
    @POST("https://9d4f-117-16-195-14.jp.ngrok.io/usersignup/")

    fun requestLogin(
        @Field("username") userid:String,
        @Field("password") password:String,
        @Field("first_name") firstname:String,
        @Field("last_name") lastname:String,
        @Field("email") email:String

    ) : Call<Register> //아웃풋 정의
}