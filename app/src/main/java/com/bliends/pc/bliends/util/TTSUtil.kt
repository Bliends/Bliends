package com.bliends.pc.bliends.util

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech

import android.speech.tts.TextToSpeech.ERROR
import android.util.Log
import java.util.*

class TTSUtil(context: Context) : TextToSpeech.OnInitListener {
    var tts : TextToSpeech = TextToSpeech(context, this)

    override fun onInit(status: Int) {
        if(status != ERROR){
            val language = tts.setLanguage(Locale.KOREAN)
            if (language == TextToSpeech.LANG_MISSING_DATA
                    || language == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("SUCESS", "TSDF")
            }else{
                Log.e("FAILLLLLLL", "TSDF")
            }
        }
    }

    fun speak(text: String){
        tts.setPitch(1.0f)
        tts.setSpeechRate(1.0f)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        // API 20
        else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun speakStop(){
        tts.stop()
        tts.shutdown()
    }
}