package com.bliends.pc.bliends.util

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RetrofitRes<T>(val context: Context): Callback<T> {

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        Log.e("어림없는 볼", "반성해라 " + t!!.message)
    }

    override fun onResponse(call: Call<T>?, response: Response<T>) {
        callback(response.code(), response.body())
    }

    abstract fun callback(code: Int, body: T?)
}