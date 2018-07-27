package com.bliends.pc.bliends.util

import android.app.Activity
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.util.Log
import android.view.View
import android.widget.EditText
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.activity.LoginActivity
import com.bliends.pc.bliends.activity.MainActivity
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.SignUp
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpUtil(context: Context) {
    var context: Context? = null

    init {
        this.context = context
    }

    fun name(view: TextInputEditText) {
//        context!!.toast("이름을 입력해 주세요")
        view.requestFocus()
        view.error = context!!.getString(R.string.signup_name_error)
    }

    fun phone(type: Boolean, view: TextInputEditText) {
        view.requestFocus()
        if (type)
            view.error = "휴대폰 번호를 입력해 주세요"
        else
            view.error = "보호자의 휴대폰 번호를 입력해 주세요"
    }

    fun id(view: TextInputEditText) {
        view.requestFocus()
        view.error = context!!.getString(R.string.signup_id_error)
    }

    fun password(view: TextInputEditText) {
        view.requestFocus()
        view.error = context!!.getString(R.string.signup_password_error)
    }

    fun cheakpassword(view: TextInputEditText) {
        view.requestFocus()
        view.error = context!!.getString(R.string.signup_cheakpassword_error)
    }


    fun signUp(name: String, id: String, type: String, phone: String, password: String, activity: Activity) {
        Log.e("test", "test")
        var res: Call<SignUp> = RetrofitUtil.postService.SignUp(
                id,
                password,
                name,
                type,
                phone
        )

        res.enqueue(object : Callback<SignUp> {

            override fun onResponse(call: Call<SignUp>?, response: Response<SignUp>?) {
                when {
                    response!!.code() == 200 -> {
                        response.body().let {
                            context!!.toast(response.body()!!.message)
                            context!!.startActivity<LoginActivity>()
                            activity.finish()
                        }
                    }
                    response.code() == 400 -> {
                        Log.e("400", response.message())
//                        context!!.toast(response.body()!!.message)
                    }
                    response.code() == 409 -> {
                        Log.e("409", response.message())
                        context!!.toast(response.body()!!.message)
                    }
                }
            }

            override fun onFailure(call: Call<SignUp>?, t: Throwable?) {
                Log.e("signuperror", t!!.message)
                context!!.toast("Sever Error")
            }
        })
    }
}