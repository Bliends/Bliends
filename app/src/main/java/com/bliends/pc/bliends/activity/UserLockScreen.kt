package com.bliends.pc.bliends.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.Help
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.service.MoneyService
import com.bliends.pc.bliends.util.GPSUtil
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.util.RetrofitUtil
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.activity_user_lock_screen.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UserLockScreen : AppCompatActivity() , GestureDetector.OnGestureListener{
    private val SWIPE_MIN_DISTANCE = 120
    private val SWIPE_MAX_OFF_PATH = 250
    private val SWIPE_THRESHOLD_VELOCITY = 200
    lateinit var gestureScanner : GestureDetector
    var latitude: Double? = null
    var longitude: Double? = null
    var time = timmer()
    var path = ""
    var file: File? = null
    var recorder: MediaRecorder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lock_screen)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        gestureScanner = GestureDetector(this)
        try {
            val intent = Intent(
                    applicationContext, //현재제어권자
                    MoneyService::class.java) // 이동할 컴포넌트
            startService(intent) // 서비스 시작
        } catch (e: IllegalArgumentException) {
            toast("블루투스가 꺼졌습니다.블루투스르 다시 켜주세요")
            TTSUtil.usingTTS(this@UserLockScreen, "블루투스가 꺼졌습니다.블루투스르 다시 켜주세요")
        }
        userCall.onClick {
            var intent = Intent(this@UserLockScreen, UserSelectActivity::class.java)
            intent.putExtra("bl", true)
            startActivityForResult(intent, 0)
            tts("전화하기\n 경찰서에 전화하시려면 왼쪽을 클릭하시고 보호자에게 전화하시려면 오른쪽을 클릭해주세요")
            userCall.backgroundColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorSub)
            callText.textColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorMain)
        }

        userCallHelp.onClick {
            time = timmer()
            Timer().schedule(time, 0, 1000)
            recordstart()
            userCallHelp.backgroundColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorSub)
            helpText.textColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorMain)
        }


        userWayLose.onClick {
            var intent = Intent(this@UserLockScreen, UserSelectActivity::class.java)
            intent.putExtra("bl", false)
            startActivityForResult(intent, 2)
            tts("길을 잃어버렸을때 보내는 요청입니다.\n 오른쪽 클릭은 보내기 왼쪽을릭은 취소입니다.")
            userWayLose.backgroundColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorSub)
            loseText.textColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorMain)
        }

        userMoney.onClick {
            var intent = Intent(this@UserLockScreen, UserSelectActivity::class.java)
            intent.putExtra("bl", false)
            startActivityForResult(intent, 3)
            tts("돈이 부족할때 보내는 요청입니다.\n 오른쪽 클릭은 보내기 왼쪽을릭은 취소입니다.")
            userMoney.backgroundColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorSub)
            moneyText.textColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorMain)
        }

        userLockFinish.onClick {
            finish()
        }
    }

    fun tts(message: String) {
        TTSUtil.usingTTS(this@UserLockScreen, message)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        loseText.textColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorbottomNav)
        callText.textColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorbottomNav)
        moneyText.textColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorbottomNav)

        userCall.backgroundColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorUserMain)
        userWayLose.backgroundColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorUserMain)
        userMoney.backgroundColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorUserMain)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                0 -> {
                    var bl = data!!.getBooleanExtra("bl", false)
                    if (bl) {
                        val pref = getSharedPreferences("UserphoneNum", Context.MODE_PRIVATE)
                        var phone = pref.getString("phoneNum", "")
                        if (phone.isEmpty()) {
                            tts("설정에서 보호자의 휴대폰번호를 등록해주세요")
                            toast("설정에서 보호자의 휴대폰번호를 등록해주세요")
                        } else {
                            startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:$phone")))
                        }
                    } else {
                        startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:112")))
                    }
                }

                2 -> {
                    Callhelp('L'.toString())
                }
                3 -> {
                    Callhelp('M'.toString())
                }
            }
        }
    }

    fun location() {
        var gps = GPSUtil(applicationContext)
        gps!!.getLocation()
        longitude = gps!!.longitude
        latitude = gps!!.latitude
        gps!!.stopUsingGPS()
    }

    fun recordstart() {
        var recodePath = "Bliends_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var dirPath = File(Environment.getExternalStorageDirectory().absolutePath, "Bliends")
        if (!dirPath.exists()) dirPath.mkdirs()
        dirPath.mkdir()
        file = File.createTempFile(recodePath, ".3gp", dirPath)//파일생성
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

    fun recordstop() {
        recorder!!.stop()
        recorder!!.reset() // setAudioSource () 단계로 돌아가서 객체를 재사용 할 수 있습니다.
        recorder!!.release()
    }

    fun timmer(): TimerTask {
        var s = 0

        var tt = object : TimerTask() {

            val handler = @SuppressLint("HandlerLeak")
            object : Handler() {
                @SuppressLint("SetTextI18n")
                override fun handleMessage(msg: Message) {
                    recordstop()
                    Callhelp("E")
                    time.cancel()
                }
            }

            override fun run() {
                Log.e("s", s.toString())
                if (s >= 10) {
                    s = 0
                    val msg = handler.obtainMessage()
                    handler.sendMessage(msg)
                }
                s++
            }
        }
        return tt
    }

    override fun onDestroy() {
        super.onDestroy()
        TTSUtil.speakStop()
    }


    fun Callhelp(situation_: String) {

        helpText.textColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorbottomNav)
        userCallHelp.backgroundColor = ContextCompat.getColor(this@UserLockScreen, R.color.colorUserMain)
        location()
        var list: List<Any> = ORMUtil(this@UserLockScreen).tokenORM.find(Sign())
        var sign = list[list.size - 1] as Sign
        var attachments: MultipartBody.Part? = null
        attachments = if (situation_ == "E") {
            val mFile = RequestBody.create(MediaType.parse("audio/*"), file)
            MultipartBody.Part.createFormData("attachments", file!!.path, mFile)
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

    override fun onTouchEvent(me : MotionEvent): Boolean {
        return gestureScanner.onTouchEvent(me)
    }

    override fun onShowPress(p0: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (p0!!.x - p1!!.x > SWIPE_MIN_DISTANCE && Math.abs(p2) > SWIPE_THRESHOLD_VELOCITY) {
            finish()
        }
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}




