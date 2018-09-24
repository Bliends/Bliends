package com.bliends.pc.bliends.util

import com.bliends.pc.bliends.data.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Services{

    @FormUrlEncoded
    @POST("sign")
    fun Sign(@Field("userid") userid: String,
             @Field("password") password: String) : Call<Sign>

    @GET("sign")
    fun UserInfo(@Header ("Authorization") Authorization : String) : Call<User>

    @FormUrlEncoded
    @POST("users")
    fun SignUp(@Field("userid") userid : String,
               @Field("password")password : String,
               @Field("name") name : String,
               @Field("type") type : String,
               @Field("phone") phone : String) : Call<SignUp>

    @GET("users")
    fun OverlapId(@Query("limit") limit: Int,
                  @Query("q") q: String?) : Call<List<User>>

    @Multipart
    @POST("helps")
    fun Help(@Header ("Authorization") Authorization : String,
             @Part("latitude") latitude: Float,
             @Part("longitude") longitude: Float,
             @Part("situation") situation : String,
             @Part attachments : MultipartBody.Part?) : Call<Help>


}