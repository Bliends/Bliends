package com.bliends.pc.bliends.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.SignUp
import com.bliends.pc.bliends.util.RetrofitUtil
import com.bliends.pc.bliends.util.SignUpUtil
import kotlinx.android.synthetic.main.activity_protector_signup2.*
import kotlinx.android.synthetic.main.activity_protector_signup3.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class ProtectorSignup3 : AppCompatActivity() {
    var pdcheck = false
    var pd = false
    var checkcheck = false
    var type : String? = null
    var name: String? = null
    var id : String? = null
    var passwd = ""
    var phone : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protector_signup3)

        type = intent.getStringExtra("type")
        if(type == "C"){
            signup3.text = "보호자 회원가입(2/3)"
        }else if(type == "P"){
            signup3.text = "사용자 회원가입(2/3)"
        }
        name = intent.getStringExtra("name")
        phone = intent.getStringExtra("number")
        id = intent.getStringExtra("id")
        Log.e("id",id)
        Log.e("name",name)
        Log.e("phone",phone)
        var Pdcheck = "^(?=.*[0-9])[a-zA-Z0-9!@#$%^&*]{8,20}$"
        Protector3Pd.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Handler().postDelayed(Runnable {
                    //흰색배경으로 덮어
                    if (pd) {
                        protector3ErrorText.visibility = View.VISIBLE
                        protector3ErrorText.text = "8 ~ 20자의 영문, 숫자로 이루어진 비밀번호를 기입해주세요"
                        protector3ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorError))
                        protector3PdView.setBackgroundResource(R.drawable.bg_login_error_et)
                        Protector3PdLayout.setHintTextAppearance(R.style.HintError)
                    }else if(checkcheck){
                        protector3ErrorText.visibility = View.VISIBLE
                        protector3ErrorText.text = "사용가능한 비밀번호 입니다."
                        protector3ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorLogin))
                        protector3PdView.setBackgroundResource(R.drawable.bg_login_select_et)
                        Protector3PdLayout.setHintTextAppearance(R.style.HintTextStyle)
                    } else {
                    protector3ErrorText.visibility = View.INVISIBLE
                    protector3ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorLogin))
                    protector3PdView.setBackgroundResource(R.drawable.bg_login_select_et)
                    protector3hintPd.visibility = View.VISIBLE
                }
            }, 100)
        } else {
            //흰색 배경을 표시/숨기기
            protector3ErrorText.visibility = View.INVISIBLE
            protector3PdView.setBackgroundResource(R.drawable.bg_login_et)
            if (Protector3Pd.text.isNotEmpty())
                protector3hintPd.visibility = View.VISIBLE
            else
                protector3hintPd.visibility = View.INVISIBLE
        }
    }

    Protector3CheckPd.onFocusChangeListener = View.OnFocusChangeListener()
    {
        v, hasFocus ->

        if (hasFocus) {
            Handler().postDelayed(Runnable {
                //흰색배경으로 덮어줌
                if (pdcheck) {
                    protector3CheckErrorText.visibility = View.VISIBLE
                    protector3CheckErrorText.text = "비밀번호가 일치합니다."
                    protector3CheckErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorLogin))
                    protector3CheckPdView.setBackgroundResource(R.drawable.bg_login_select_et)
                    Protector3CheckPdLayout.setHintTextAppearance(R.style.HintTextStyle)
                } else {
                    protector3CheckErrorText.visibility = View.VISIBLE
                    protector3CheckErrorText.text = "비밀번호가 일치하지 않습니다."
                    protector3CheckErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorError))
                    protector3CheckPdView.setBackgroundResource(R.drawable.bg_login_error_et)
                    Protector3CheckPdLayout.setHintTextAppearance(R.style.HintError)
                }
                Protector3HintCheckPd.visibility = View.VISIBLE
            }, 100)
        } else {
            //흰색 배경을 표시/숨기기
            protector3CheckErrorText.visibility = View.INVISIBLE
            protector3CheckPdView.setBackgroundResource(R.drawable.bg_login_et)
            if (Protector3CheckPd.text.isNotEmpty())
                Protector3HintCheckPd.visibility = View.VISIBLE
            else
                Protector3HintCheckPd.visibility = View.INVISIBLE
        }
    }

    protector3SeeCheckPd.onClick{
        if (Protector3CheckPd.inputType == 129) {
            Protector3CheckPd.inputType = InputType.TYPE_CLASS_TEXT
            protector3SeeCheckPd.setBackgroundResource(R.drawable.rock)
        } else if (Protector3CheckPd.inputType == InputType.TYPE_CLASS_TEXT) {
            Protector3CheckPd.inputType = 129
            protector3SeeCheckPd.setBackgroundResource(R.drawable.unrock)
        }
    }

    protector3SeePd.onClick{
        if (Protector3Pd.inputType == 129) {
            Protector3Pd.inputType = InputType.TYPE_CLASS_TEXT
            protector3SeePd.setBackgroundResource(R.drawable.rock)
        } else if (Protector3Pd.inputType == InputType.TYPE_CLASS_TEXT) {
            Protector3Pd.inputType = 129
            protector3SeePd.setBackgroundResource(R.drawable.unrock)
        }
    }

    Protector3CheckPd.addTextChangedListener(
    object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.isEmpty()) {
                protector3SeeCheckPd.visibility = View.INVISIBLE
            } else if (s.isNotEmpty()) {
                protector3SeeCheckPd.visibility = View.VISIBLE
                if (Protector3CheckPd.text.toString() != Protector3Pd.text.toString()) {
                    protector3CheckErrorText.visibility = View.VISIBLE
                    protector3CheckErrorText.text = "비밀번호가 일치하지 않습니다."
                    pdcheck = false
                    protector3CheckErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorError))
                    protector3CheckPdView.setBackgroundResource(R.drawable.bg_login_error_et)
                    Protector3CheckPdLayout.setHintTextAppearance(R.style.HintError)
                } else if (Protector3CheckPd.text.toString() == Protector3Pd.text.toString()) {
                    protector3CheckErrorText.visibility = View.VISIBLE
                    protector3CheckErrorText.text = "비밀번호가 일치합니다."
                    pdcheck = true
                    protector3CheckErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorLogin))
                    protector3CheckPdView.setBackgroundResource(R.drawable.bg_login_select_et)
                    Protector3CheckPdLayout.setHintTextAppearance(R.style.HintTextStyle)
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable) {}
    })

    Protector3Pd.addTextChangedListener(
    object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.isEmpty()) {
                protector3SeePd.visibility = View.INVISIBLE
            } else if (s.isNotEmpty()) {
                protector3SeePd.visibility = View.VISIBLE
                if (Protector3CheckPd.text.toString() != Protector3Pd.text.toString()) {
                    pdcheck = false
                } else if (Protector3CheckPd.text.toString() == Protector3Pd.text.toString()) {
                    pdcheck = true
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable) {}
    })

    protector3NextBtn.onClick{
        val pdm = Pattern.matches(Pdcheck, Protector3Pd.text)
        if (!pdm) {
            pd = true
            protector3ErrorText.visibility = View.VISIBLE
            protector3ErrorText.text = "8 ~ 20자의 영문, 숫자로 이루어진 비밀번호를 기입해주세요"
            protector3ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorError))
            protector3PdView.setBackgroundResource(R.drawable.bg_login_error_et)
            Protector3PdLayout.setHintTextAppearance(R.style.HintError)
            Protector3Pd.requestFocus()
            checkcheck = false
        } else if (pdm && !pdcheck) {
            pd = false
            protector3ErrorText.visibility = View.VISIBLE
            protector3ErrorText.text = "사용가능한 비밀번호 입니다."
            protector3ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorLogin))
            protector3PdView.setBackgroundResource(R.drawable.bg_login_select_et)
            Protector3PdLayout.setHintTextAppearance(R.style.HintTextStyle)
            checkcheck = true
            Protector3Pd.requestFocus()
        } else if (pdm && pdcheck) {
            protector3ErrorText.visibility = View.VISIBLE
            protector3ErrorText.text = "사용가능한 비밀번호 입니다."
            passwd = Protector3Pd.text.toString()
            protector3ErrorText.setTextColor(ContextCompat.getColor(this@ProtectorSignup3, R.color.colorLogin))
            protector3PdView.setBackgroundResource(R.drawable.bg_login_select_et)
            Protector3PdLayout.setHintTextAppearance(R.style.HintTextStyle)
            Log.e("passwd",passwd)
           signUp()
        }
    }

    protector3Back.onClick{
        startActivity<ProtectorSignup2>()
        finish()
    }
}

    fun signUp() {
        Log.e("test", "test")
        var res: Call<SignUp> = RetrofitUtil.postService.SignUp(
                id!!,
                passwd,
                name!!,
                type!!,
                phone!!
        )

        res.enqueue(object : Callback<SignUp> {

            override fun onResponse(call: Call<SignUp>?, response: Response<SignUp>?) {
                when{
                    response!!.code() == 200 -> {
                        response.body().let {
                            toast(name + "님의 회원가입이 정상적으로 완료되었습니다.")
                            startActivity<LoginActivity>()
                            finish()
                        }
                    }
                    else ->{
                        Log.e("errorcode",response!!.code().toString())
                        val ErrorObj = JSONObject(response.errorBody()!!.string())
                        toast(ErrorObj.getString("message"))
                    }
                }
            }

            override fun onFailure(call: Call<SignUp>?, t: Throwable?) {
                Log.e("signuperror", t!!.message)
                toast("Sever Error")
            }
        })
    }
}
