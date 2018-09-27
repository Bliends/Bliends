package com.bliends.pc.bliends.fragment

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.util.Log
import android.view.View
import com.bliends.pc.bliends.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.WindowManager
import com.bliends.pc.bliends.activity.UserSelectActivity
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.util.RetrofitRes
import com.bliends.pc.bliends.util.RetrofitUtil
import com.bliends.pc.bliends.util.TTSUtil
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.dialog_change_password.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

@SuppressLint("ValidFragment")
class ChangePasswordDialogFragment(user: String) : DialogFragment(), View.OnClickListener {
    private var mDialog: Dialog? = null

    val user = user
    var client: User? = null
    var token: Sign? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = Dialog(activity, R.style.DialogFragment)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(mDialog!!.window.attributes)

        val view = View.inflate(activity, R.layout.dialog_change_password, null)
        mDialog!!.window.attributes = layoutParams
        mDialog!!.setContentView(view)

        return mDialog as Dialog
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_change.setOnClickListener(this)
        try {
            val Ulist = ORMUtil(context!!).userORM.find(User())
            val TList = ORMUtil(context!!).tokenORM.find(Sign())
            client = Ulist[Ulist.size - 1] as User
            token = TList[TList.size - 1] as Sign
        } catch (e: Exception) {
            toast("유저 정보가 없습니다!!")
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.dialog_change_password, container, false)

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
                val password = change_password_edit.text.toString()
                val body = JsonObject()
                        .apply {
                            addProperty("password", password)
                            addProperty("name", client!!.name)
                            addProperty("type", client!!.type)
                            addProperty("phone", client!!.phone)
                        }

/*                RetrofitUtil.postService.getUser().enqueue(object : RetrofitRes<ArrayList<User>>(context!!) {
                    override fun callback(code: Int, body: ArrayList<User>?) {
                        if (code == 200) {
                            body!!.forEach {
                                Log.e("LIST", it._id.toString() + " " + it.name + " " + it.userid + " ")
                            }
                        }else{
                            Log.e("FAILL!!", code.toString())
                        }
                    }
                })*/


                if (change_password_edit.text.toString() == change_password_edit_two.text.toString() && change_password_edit.length() > 0) {
                    if (user == "Protector") {
                        Log.e("asdf", password)
                        RetrofitUtil.postService.patchUser(
                                token!!.token,
                                client!!._id,
                                body).enqueue(object : RetrofitRes<User>(context!!) {
                            override fun callback(code: Int, body: User?) {
                                if (code == 201) {
                                    toast("비밀번호가 변경되었습니다.")
                                    dismiss()
                                } else {
                                    Log.e("FAIL", code.toString() + " " + client!!._id + " " + client!!.name + " " + client!!.type + " " + client!!.phone)
                                    //dismiss()
                                    //toast("비밀번호가 변경되었습니다.")
                                    toast("비밀번호 변경을 실패했습니다.")
                                }
                            }
                        })
                    } else if (user == "User") {
                        tts("설정하신 보호자의 비밀번호가 맞으신가요?\n" + "맞으시면 오른쪽 클릭 왼쪽을릭은 취소입니다.")
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
                val password = change_password_edit.text.toString()
                val body = JsonObject()
                        .apply {
                            addProperty("password", password)
                            addProperty("name", client!!.name)
                            addProperty("type", client!!.type)
                            addProperty("phone", client!!.phone)
                        }
                RetrofitUtil.postService.patchUser(
                        token!!.token,
                        client!!._id,
                        body).enqueue(@TargetApi(Build.VERSION_CODES.M)
                object : RetrofitRes<User>(context!!) {
                    override fun callback(code: Int, body: User?) {
                        if (code == 201) {
                            toast("비밀번호가 변경되었습니다")
                            dismiss()
                        } else {
                            toast("비밀번호 변경을 실패했습니다.")
                        }
                    }
                })
                tts("설정하신 비밀번호가 정상적으로 설정되었습니다.")
                toast("설정하신 비밀번호가 정상적으로 설정되었습니다.")
            } else {
                dismiss()
                Log.e("tset", "취소")
                tts("비밀번호 변경을 취소하였습니다.")
                toast("비밀번호 변경을 취소하였습니다.")
            }
        }
    }
}