package com.bliends.pc.bliends.util

import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import com.bliends.pc.bliends.data.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface Services{

    @FormUrlEncoded
    @POST("sign")
    fun Sign(@Field("userid") userid: String,
             @Field("password") password: String) : Call<Sign>

    @GET("sign")
    fun UserInfo(@Header ("Authorization") Authorization : String) : Call<UserInfo>
}