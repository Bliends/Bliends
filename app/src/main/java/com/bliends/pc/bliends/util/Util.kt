package com.bliends.pc.bliends.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Util{

//    http://norr.uy.to:5000/api
val URL = "http://norr.uy.to:5000/api/"

    var retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val postService = retrofit!!.create(Services::class.java)
}