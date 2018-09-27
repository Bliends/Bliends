package com.bliends.pc.bliends.util

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.tts.TextToSpeech

import android.speech.tts.TextToSpeech.SUCCESS
import android.util.Log
import java.util.*
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.speech.SpeechRecognizer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import com.bliends.pc.bliends.activity.TutorialUser1
import com.bliends.pc.bliends.activity.UserMainActivity
import org.jetbrains.anko.startActivity


@SuppressLint("StaticFieldLeak")
object TTSUtil : TextToSpeech.OnInitListener {
    var handler = Handler()
    private var tts: TextToSpeech? = null
    lateinit var text: String
    lateinit var context: Context
    var blbl: Boolean = false
    var recognizer: SpeechRecognizer? = null
    var bl: Boolean = false
    var resoundcheck: Boolean = false
    var check: String = ""

    fun usingTTS(context: Context, text: String) {
        tts = TextToSpeech(context, this)
        this.text = text
        this.bl = false
    }

    fun TutroailusingTTS(context: Context, text: String, check: String, resoundcheck: Boolean) {
        tts = TextToSpeech(context, this)
        this.text = text
        this.check = check
        this.bl = true
        this.context = context
        this.resoundcheck = resoundcheck
    }

    override fun onInit(status: Int) {
        if (status == SUCCESS) {
            val language = this.tts!!.setLanguage(Locale.KOREA)
            if (language == TextToSpeech.LANG_MISSING_DATA
                    || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "Language is not available.")
            } else {
                if (bl) {
                        tutroialspeak(this.text)
                } else {
                    speak(this.text)
                }
            }
        }
    }

        fun SoundOn() {
            handler.removeMessages(0)
            var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
            recognizer = SpeechRecognizer.createSpeechRecognizer(context)
            recognizer!!.setRecognitionListener(listener)
            recognizer!!.startListening(intent)
        }

        fun tutroialspeak(text: String) {
            tts!!.setPitch(1.0f)
            tts!!.setSpeechRate(1.0f)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                sound()
                // API 20
            } else {
                tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
                sound()
            }
        }

        fun sound() {
            Log.e("check", check)
            Log.e("check", resoundcheck.toString())
            if (check == "Start" && !resoundcheck) {
                Log.e("asdfsd", "asdfsadf")
                handler.postDelayed({
                    SoundOn()
                }, 13000)
            } else if (resoundcheck) {
                Log.e("hjgfgd", "jhgfasdfasdfasdfasdfadfasdfasdfasd")
                handler.postDelayed({
                    SoundOn()
                }, 3500)
            }
        }

        //음성인식 리스너
        private val listener = object : RecognitionListener {
            //음성 인식 준비가 되었으면
            override fun onReadyForSpeech(bundle: Bundle) {

            }

            //입력이 시작되면
            override fun onBeginningOfSpeech() {

            }

            //입력 소리 변경 시
            override fun onRmsChanged(v: Float) {

            }

            //더 많은 소리를 받을 때
            override fun onBufferReceived(bytes: ByteArray) {

            }

            //음성 입력이 끝났으면
            override fun onEndOfSpeech() {

            }

            //에러가 발생하면
            override fun onError(i: Int) {
                TutroailusingTTS(context, "읆성인식에 실패하였습니다. 다시 말해주세요", check, true)
            }

            //음성 인식 결과 받음
            override fun onResults(results: Bundle) {
                Log.e("음성인식", "성공")
                val key = SpeechRecognizer.RESULTS_RECOGNITION
                val result = results.getStringArrayList(key)
                val rs = arrayOfNulls<String>(result!!.size)
                result.toArray(rs)
                Log.e("rs", rs[0])
                if (rs[0] == "예" || rs[0] == "네" || rs[0] == "시작" || rs[0] == "시작하기") {
                    if (check == "Start") {
                        context.startActivity<UserMainActivity>()
                    }
                } else {
                    TutroailusingTTS(context, "읆성인식에 실패하였습니다. 다시 말해주세요", check, true)
                }
            }

            //인식 결과의 일부가 유효할 때
            override fun onPartialResults(bundle: Bundle) {

            }

            //미래의 이벤트를 추가하기 위해 미리 예약되어진 함수
            override fun onEvent(i: Int, bundle: Bundle) {

            }
        }


        private fun speak(text: String) {
            tts!!.setPitch(1.0f)
            tts!!.setSpeechRate(1.0f)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            // API 20
            else
                tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }

        fun speakStop() {
            if (tts != null) {
                tts!!.stop()
                tts!!.shutdown()
            }
            if (recognizer != null) {
                recognizer!!.setRecognitionListener(listener)
            }
        }
    }