package com.bliends.pc.bliends.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import com.bliends.pc.bliends.data.UserInfo
import com.bliends.pc.bliends.util.RetrofitUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.jetbrains.anko.toast
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginSignup.setOnClickListener {
            startActivity<SignupSelectActivity>()
        }

        loginBtn.setOnClickListener {
            Login()
        }
    }

    fun Login() {
        var res: Call<Sign> = RetrofitUtil.postService.Sign(loginId.text.toString(), loginPasswd.text.toString())

        res.enqueue(object : Callback<Sign> {

            override fun onResponse(call: Call<Sign>?, response: Response<Sign>?) {
//                Log.e("login" + response!!.code().toString(), response.body()!!.message)
                when {
                    response!!.code() == 200 -> response.body()?.let {
                        toast(response.body()!!.message)
                        var token = response.body()!!.token
                        GetUser(token)
                        ORMUtil(this@LoginActivity).tokenORM.save(response.body()!!)
                        startActivity<MainActivity>()
                        finish()
                    }
                    else ->{
                        val ErrorObj = JSONObject(response.errorBody()!!.string())
                        toast(ErrorObj.getString("message"))
                    }

                }
            }

            override fun onFailure(call: Call<Sign>?, t: Throwable?) {
                toast("Sever Error")
                Log.e("login Error", t!!.message)
            }
        })
    }

    fun GetUser(token: String) {
        var res: Call<UserInfo> = RetrofitUtil.postService.UserInfo(token)
        res.enqueue(object : Callback<UserInfo> {

            override fun onResponse(call: Call<UserInfo>?, response: Response<UserInfo>?) {
                when {
                    response!!.code() == 200 -> response.body().let {
                        Log.e("name", Gson().toJson(response.body()!!))
                        ORMUtil(this@LoginActivity).userORM.save(response.body()!!.user!!)
                    }
                    else ->{
                        val ErrorObj = JSONObject(response.errorBody()!!.string())
                        toast(ErrorObj.getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>?, t: Throwable?) {
                Log.e("getuser Error", t!!.message)
                toast("Sever Error")
            }
        })
    }

}
