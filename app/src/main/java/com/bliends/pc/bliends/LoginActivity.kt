package com.bliends.pc.bliends

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var intent = Intent(this,MainActivity::class.java)
        loginBtn.setOnClickListener {
            var res: Call<Sign> = Util.postService.Sign(loginId.text.toString(),loginPasswd.text.toString())
            res.enqueue(object : Callback<Sign> {
                override fun onFailure(call: Call<Sign>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
                    Log.e("retrofit Error", t!!.message)
                }

                override fun onResponse(call: Call<Sign>?, response: Response<Sign>?) {
                    Log.e(response!!.code().toString(),response.message())
                    if (response!!.code() == 200) {
                        response.body()?.let {
                            //token저장
                            var pres = getSharedPreferences("pres", Context.MODE_PRIVATE)
                            var editer = pres.edit()
                            editer.putString("token",response.body()!!.token)
                            editer.commit()

                            startActivity(intent)
                        }
                    }else if(response!!.code() == 403){
                        Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                    }else if(response!!.code() == 401){
                        Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}
