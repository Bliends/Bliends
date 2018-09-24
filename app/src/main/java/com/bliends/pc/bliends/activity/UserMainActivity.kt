package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bliends.pc.bliends.R
import android.R.menu
import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import com.bliends.pc.bliends.data.Help
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.util.RetrofitUtil
import kotlinx.android.synthetic.main.activity_user_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class UserMainActivity : AppCompatActivity() {
    var time = timmer()
    var path = ""
    var recorder: MediaRecorder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
        setSupportActionBar(userToolbar)

        userCall.onClick {
            startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:01094732771")))
        }

        userCallHelp.onClick {
            time = timmer()
            Timer().schedule(time, 0, 1000)
            recordstart()
            Callhelp("E")
        }

        userWayLose.onClick {
            Callhelp("L")
        }

        userMoney.onClick {
            Callhelp("M")
        }
    }

    fun recordstart() {
        val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/Bliends/"
        var file = File(dirPath)
        if (!file.exists()) file.mkdirs()
        var recodePath = "Bliends" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        path = "$dirPath$recodePath.3gp"
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(path)
            prepare()
            start()
        }
    }

    fun timmer(): TimerTask {

        var s = 0

        var tt = object : TimerTask() {
            override fun run() {
                s++
                if (s >= 10) {
                    recorder!!.release()
                    recorder = null
                    time.cancel()
                    s = 0
                }
            }
        }
        return tt
    }
        fun Callhelp(situation: String) {
            var list: List<Any> = ORMUtil(this@UserMainActivity).tokenORM.find(Sign())
            var sign = list[list.size - 1] as Sign
            var attachments : String? = null
            if(situation == "E") {
                attachments = "file://$path"
            }else{
                attachments = null
            }
            var res = RetrofitUtil.postService.Help(
                    sign.token,
                    0.0f,
                    0.0f,
                    situation,
                            RetrofitUtil.audioMultipartBody(attachments!!, "profileImg")
            )

            res.enqueue(object : Callback<Help> {
                override fun onResponse(call: Call<Help>, response: Response<Help>) {
                    when (response.code()) {
                        201 -> {
                            Log.e("help", "ok")
                            if (situation != "E") {
                                toast("정상적으로 발송이 완료되었습니다.")
                            }
                        }

                        else -> {
                            Log.e(response.code().toString(),response.message())
                        }
                    }
                }

                override fun onFailure(call: Call<Help>, t: Throwable) {
                    Log.e("Help Error", t!!.message)
                    toast("Sever Error")
                }
            })
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.user_menu, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.userSetting -> {
                    startActivity<LoginActivity>()
                }
            }
            return super.onOptionsItemSelected(item)
        }
    }
