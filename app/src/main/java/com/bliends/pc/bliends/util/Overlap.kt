package com.bliends.pc.bliends.util

import android.app.Activity
import android.util.Log
import com.bliends.pc.bliends.data.SignUp
import com.bliends.pc.bliends.data.User
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call

class Overlap(id: String, activity: Activity) : Thread() {
    var id = id
    var activity = activity
    var Overlapstring = ""
    var json: String = "{\"userid\" : \"$id\"}"

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
                        Overlapstring = "중복"
                    }
                } catch (e: IndexOutOfBoundsException) {
                    Overlapstring = ""
                }
            } else {
                val ErrorObj = JSONObject(res.execute().errorBody()!!.string())
                activity.toast(ErrorObj.getString("message"))
            }
        } catch (e: JsonSyntaxException) {
            Overlapstring = "중복"
        }
    }

    fun OverlapData(): String {
        return Overlapstring
    }
}