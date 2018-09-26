package com.bliends.pc.bliends.fragment

import android.annotation.SuppressLint
import android.annotation.TargetApi
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
import android.view.View
import com.bliends.pc.bliends.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.WindowManager
import kotlinx.android.synthetic.main.dialog_change_phone.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.toast

class ChangePhoneDialogFragment : DialogFragment(), View.OnClickListener{

    private var mDialog : Dialog? = null

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

    @TargetApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_change -> {
                if(change_phone_edit.length() == 13) {
                    val pref = context?.getSharedPreferences("phoneNum", MODE_PRIVATE)
                    with(pref!!.edit()) {
                        putString("phoneNum", "${change_phone_edit.text}")
                        apply()
                    }
                    dismiss()
                }else{
                    toast("다시 입력하세요!")
                }
            }
        }
    }
}