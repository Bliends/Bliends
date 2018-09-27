package com.bliends.pc.bliends.util

import com.bliends.pc.bliends.data.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface Services{

    @FormUrlEncoded
    @POST("sign")
    fun Sign(@Field("userid") userid: String,
             @Field("password") password: String) : Call<Sign>

    @GET("helps/{help_id}")
    fun useIdGetHelp(@Header ("Authorization") Authorization : String,
                      @Path("help_id") help_id: Int) : Call<Help>

    @GET("activitylogs/{activitylog_id}")
    fun useIdGetActivityLog(@Header ("Authorization") Authorization : String,
                             @Path("activitylog_id") activitylog_id: Int) : Call<ActivityLog>

    @GET("labels/{label_id}")
    fun useIdGetLabel(@Header ("Authorization") Authorization : String,
                       @Path("label_id") label_id: Int) : Call<Label>

    @GET("helps")
    fun getHelpList(@Header ("Authorization") Authorization : String) : Call<ArrayList<Help>>

    @GET("activitylogs")
    fun getActivityLogList(@Header ("Authorization") Authorization : String) : Call<ArrayList<ActivityLog>>

    @GET("labels")
    fun getLabelList(@Header ("Authorization") Authorization : String) : Call<ArrayList<Label>>

    @FormUrlEncoded
    @POST("helps")
    fun postHelp(@Header ("Authorization") Authorization : String,
                 @Field("latitude") latitude : Float,
                 @Field("longitude") longitude : Float,
                 @Field("situation") situation : String,
                 @Field("attachments") attachments : File?) : Call<Help>

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
             @Part("latitude") latitude: Double,
             @Part("longitude") longitude: Double,
             @Part("situation") situation : String,
             @Part attachments : MultipartBody.Part?) : Call<Help>

    @FormUrlEncoded
    @POST("activitylogs")
    fun postActivityLog(@Header ("Authorization") Authorization : String,
                        @Field("label") label : Int,
                        @Field("latitude") latitude : Float,
                        @Field("longitude") longitude : Float,
                        @Field("payments") payments : Int) : Call<ActivityLog>

    @FormUrlEncoded
    @POST("labels")
    fun postLabel(@Header ("Authorization") Authorization : String,
                        @Field("name") name : String,
                        @Field("latitude") latitude : Float,
                        @Field("longitude") longitude : Float,
                        @Field("importance") importance : Int) : Call<Label>

    @GET("dashboard/by-date")
    fun getDashBoardByDate(@Header ("Authorization") Authorization : String,
                           @Query("limit") limit : Int) : Call<ArrayList<DashBoardDate>>

    @GET("dashboard/by-label")
    fun getDashBoardByLabel(@Header ("Authorization") Authorization : String) : Call<ArrayList<DashBoardLabel>>

    @PATCH("users/{user_id}")
    fun patchUser(@Header ("Authorization") Authorization : String,
                  @Path("user_id") user_id : Int,
                  @Body body : JsonObject) : Call<User>

    @PATCH("users")
    fun getUser() : Call<ArrayList<User>>
}