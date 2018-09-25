package com.bliends.pc.bliends.util

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitUtil{

//    http://norr.uy.to:5000/api
    val URL = "http://norr.uy.to:5000/api/"

    var retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val postService = retrofit!!.create(Services::class.java)

    fun audioMultipartBody(file : File, name: String): MultipartBody.Part {
        val mFile = RequestBody.create(MediaType.parse("audio/*"), file)
        return MultipartBody.Part.createFormData(name, file.path, mFile)
    }
}