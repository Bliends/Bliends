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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            Login()
        }
    }

    fun Login() {
        var intent = Intent(this, MainActivity::class.java)
        var res: Call<Sign> = RetrofitUtil.postService.Sign(loginId.text.toString(), loginPasswd.text.toString())

        res.enqueue(object : Callback<Sign> {

            override fun onResponse(call: Call<Sign>?, response: Response<Sign>?) {
                Log.e("login" + response!!.code().toString(), response.message())
                when {
                    response!!.code() == 200 -> response.body()?.let {
                        var token = response.body()!!.token
                        GetUser(token)
                        ORMUtil(this@LoginActivity).tokenORM.save(response.body()!!)
                        startActivity(intent)
                        finish()
                    }
                    response!!.code() == 403 -> toast("유효하지 않은 사용자명이나 암호입니다.")
                    response!!.code() == 401 -> toast(response.message())
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
                    response!!.code() == 401 -> Log.e("getUser 401", response.message())
                }
            }

            override fun onFailure(call: Call<UserInfo>?, t: Throwable?) {
                Log.e("getuser Error", t!!.message)
                toast("Sever Error")
            }
        })
    }

}
