package com.bliends.pc.bliends.util

import com.bliends.pc.bliends.data.Sign
import retrofit2.Call
import retrofit2.http.*

interface Services{

    @FormUrlEncoded
    @POST("sign")
    fun Sign(@Field("userid") userid: String,
             @Field("password") password: String) : Call<Sign>

    @GET("sign")
    fun GetSign(@Header ("Authorization") authorization : String) : Call<Sign>
}