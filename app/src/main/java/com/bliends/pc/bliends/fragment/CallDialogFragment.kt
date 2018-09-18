package com.bliends.pc.bliends.fragment

import android.app.Dialog
import android.app.DialogFragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View
import com.bliends.pc.bliends.R
import android.view.ViewGroup
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.dialog_call.*
import android.view.WindowManager

class CallDialogFragment : DialogFragment(), View.OnClickListener{

    private var mDialog : Dialog? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = Dialog(activity, R.style.DialogFragment)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mDialog!!.window.attributes)

        val view = View.inflate(activity, R.layout.dialog_call, null)
        mDialog!!.window.attributes = layoutParams
        mDialog!!.setContentView(view)

        return mDialog as Dialog
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        call_weak_person.setOnClickListener(this)
        call_police.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.dialog_call, container, false)

        mDialog!!.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        return v
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.call_weak_person -> startActivity(Intent("android.intent.action.DIAL", Uri.parse("tel:01097432771")))
            R.id.call_police -> startActivity(Intent("android.intent.action.DIAL", Uri.parse("tel:112")))
            R.id.btn_cancel -> dismiss()
        }
    }
}