package com.bliends.pc.bliends.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bliends.pc.bliends.R
import android.R.menu
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.WindowManager
import com.bliends.pc.bliends.data.Help
import com.bliends.pc.bliends.util.RetrofitUtil
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import com.bliends.pc.bliends.util.GPSUtil
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.activity_user_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class UserMainActivity : AppCompatActivity() {
    var latitude : Double? = null
    var longitude : Double? = null
    var time = timmer()
    var path = ""
    var file : File? = null
    var recorder: MediaRecorder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)
        setSupportActionBar(userToolbar)


        userCall.onClick {
            var intent = Intent(this@UserMainActivity,UserSelectActivity::class.java)
            intent.putExtra("bl",true)
            startActivityForResult(intent,0)
            tts("전화하기\n 경찰서에 전화하시려면 왼쪽을 클릭하시고 보호자에게 전화하시려면 오른쪽을 클릭해주세요")
        }

        userCallHelp.onClick {
            var intent = Intent(this@UserMainActivity,UserSelectActivity::class.java)
            intent.putExtra("bl",false)
            startActivityForResult(intent,1)
        }

        userWayLose.onClick {
            var intent = Intent(this@UserMainActivity,UserSelectActivity::class.java)
            intent.putExtra("bl",false)
            startActivityForResult(intent,2)
            tts("길을 잃어버렸을때 보내는 요청입니다.\n 오른쪽 클릭은 보내기 왼쪽을릭은 취소입니다.")
        }

        userMoney.onClick {
            var intent = Intent(this@UserMainActivity,UserSelectActivity::class.java)
            intent.putExtra("bl",false)
            startActivityForResult(intent,3)
            tts("돈이 부족할때 보내는 요청입니다.\n 오른쪽 클릭은 보내기 왼쪽을릭은 취소입니다.")
        }
    }

    fun tts(message: String){
    TTSUtil.usingTTS(this@UserMainActivity,message)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                    0->{
                        var bl = data!!.getBooleanExtra("bl", false)
                        if (bl) {
                            startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:01094732771")))
                        } else {
                            startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:112")))
                        }
                    }
                    1 -> {
                        recordstart()
//                        loaction()
                    }

                    2 -> {
                        Callhelp('L'.toString())
//                        loaction()
                    }
                    3 -> {
                        Callhelp('M'.toString())
//                        loaction()
                    }
                }
        }
    }

    fun location(){
        var gps = GPSUtil(applicationContext)
        gps!!.getLocation()
        longitude = gps!!.longitude
        latitude = gps!!.latitude
        gps!!.stopUsingGPS()
    }

    fun recordstart() {
        time = timmer()
        Timer().schedule(time, 0, 1000)
        var recodePath = "Bliends_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var dirPath = File(Environment.getExternalStorageDirectory().absolutePath,"Bliends")
        dirPath.mkdir()
        file= File.createTempFile(recodePath,".3gp",dirPath)//파일생성
        path = file!!.path
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
                Log.e("s",s.toString())
                if (s >= 10) {
                    Callhelp("E")
                    recorder!!.release()
                    recorder = null
                    this.cancel()
                    s = 0
                }
                s++
            }
        }
        return tt
    }




    fun Callhelp(situation_: String) {
        location()
        var list: List<Any> = ORMUtil(this@UserMainActivity).tokenORM.find(Sign())
        var sign = list[list.size - 1] as Sign
        var attachments: MultipartBody.Part? = null
        attachments = if (situation_ == "E") {
            val mFile = RequestBody.create(MediaType.parse("audio/*"), file)
            MultipartBody.Part.createFormData("attachments",file!!.path,mFile)
        } else {
            null
        }

        Log.e("situation", situation_)
        var res = RetrofitUtil.postService.Help(
                sign.token,
                latitude!!,
                longitude!!,
                situation_,
                attachments
        )

        res.enqueue(object : Callback<Help> {
            override fun onResponse(call: Call<Help>, response: Response<Help>) {
                when (response.code()) {
                    201 -> {
                        Log.e("help", "ok")
                        if (situation_ != "E") {
                            toast("정상적으로 발송이 완료되었습니다.")
                        }
                    }

                    else -> {
                        Log.e(response.code().toString(), response.message())
                        val ErrorObj = JSONObject(response.errorBody()!!.string())
                        toast(ErrorObj.getString("message"))
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
                //테스트로 로그인 부분으로 넘어가게 해놨음
                ORMUtil(this).tokenORM.delete(Sign())
                ORMUtil(this).userORM.delete(User())
                startActivity<LoginActivity>()
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }


}
