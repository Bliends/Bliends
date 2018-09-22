package com.bliends.pc.bliends.util

import android.app.Activity
import android.util.Log
import com.bliends.pc.bliends.data.User
import com.google.gson.JsonSyntaxException
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call

class UserPhoneNumbarCheck(phone: String, activity: Activity) : Thread() {
    var phone = phone
    var activity = activity
    var json = "{\"type\":\"P\",\"phone\":\"$phone\"}"
    var numbarcheckString = ""
    var res: Call<List<User>> = RetrofitUtil.postService.OverlapId(
            1,
            json
    )

    override fun run() {
        try {
            if (res.clone().execute().code() == 200) {
                Log.e("testest", json)
                try {
                    if (res.execute().body()!![0].userid != "") {
                        numbarcheckString = "존재"
                    }
                } catch (e: IndexOutOfBoundsException) {
                    numbarcheckString = ""
                }
            } else {
                val ErrorObj = JSONObject(res.execute().errorBody()!!.string())
                activity.toast(ErrorObj.getString("message"))
            }
        }catch (e : JsonSyntaxException){
            numbarcheckString = "존재"
        }
    }

    fun  numbarCheckData() : String{
        return numbarcheckString
    }
}