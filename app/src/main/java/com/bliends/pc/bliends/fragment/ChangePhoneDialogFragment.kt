package com.bliends.pc.bliends.fragment

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.View
import com.bliends.pc.bliends.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.WindowManager
import com.bliends.pc.bliends.activity.UserSelectActivity
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.dialog_change_phone.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@SuppressLint("ValidFragment")
class ChangePhoneDialogFragment(user: String) : DialogFragment(), View.OnClickListener {
    var user = user
    private var mDialog: Dialog? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = Dialog(activity, R.style.DialogFragment)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mDialog!!.window.attributes)

        val view = View.inflate(activity, R.layout.dialog_change_phone, null)
        mDialog!!.window.attributes = layoutParams
        mDialog!!.setContentView(view)

        return mDialog as Dialog
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = context?.getSharedPreferences("phoneNum", MODE_PRIVATE)
        change_phone_edit.hint = pref!!.getString("phoneNum", "전화 번호를 입력하세요")
        change_phone_edit.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        btn_change.setOnClickListener(this)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.dialog_change_phone, container, false)

        mDialog!!.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        return v
    }


    fun tts(message: String) {
        TTSUtil.usingTTS(activity, message)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_change -> {
                if (change_phone_edit.length() == 13) {

                    if (user == "Protector") {
                        val pref = context?.getSharedPreferences("phoneNum", MODE_PRIVATE)
                        with(pref!!.edit()) {
                            putString("phoneNum", "${change_phone_edit.text}")
                            apply()
                        }
                        dismiss()
                    } else if (user == "User") {
                        tts("설정하신 보호자의 휴대폰 번호가${change_phone_edit.text}(이)가 맞으신가요?\n" + "맞으시면 오른쪽 클릭 왼쪽을릭은 취소입니다.")
                        var intent = Intent(activity, UserSelectActivity::class.java)
                        intent.putExtra("bl", false)
                        startActivityForResult(intent, 1)
                    }
                } else {
                    toast("다시 입력하세요!")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            Log.e("tset", "asdfa")
            if (resultCode == Activity.RESULT_OK) {
                Log.e("tset", "완료")
                val pref = activity?.getSharedPreferences("UserphoneNum", MODE_PRIVATE)
                with(pref!!.edit()) {
                    putString("phoneNum", "${change_phone_edit.text}")
                    apply()
                }
                dismiss()
                tts("설정하신 보호자의 휴대폰번호가 정상적으로 등록되었습니다.")
                toast("설정하신 보호자의 휴대폰번호가 정상적으로 등록되었습니다.")
            } else {
                dismiss()
                Log.e("tset", "취소")
                tts("휴대폰번호 변경을 취소하였습니다.")
                toast("휴대폰번호 변경을 취소하였습니다.")
            }
        }
    }
}