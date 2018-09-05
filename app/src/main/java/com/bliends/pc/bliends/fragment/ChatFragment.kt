package com.bliends.pc.bliends.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.adapter.ChatLogAdapter
import com.bliends.pc.bliends.data.Chat
import com.bliends.pc.bliends.data.Chat.Companion.TYPE_MY_CHAT
import com.bliends.pc.bliends.data.Chat.Companion.TYPE_OTHER_CHAT
import com.bliends.pc.bliends.data.Chat.Companion.TYPE_TIME_CHAT
import kotlinx.android.synthetic.main.fragment_chat.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast

class ChatFragment : Fragment() {
    var mChatLog = ArrayList<Chat>()
    lateinit var mChatAdapter : ChatLogAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mChatAdapter = ChatLogAdapter(mChatLog)
        recycler_chat_log.adapter = mChatAdapter
        recycler_chat_log.setHasFixedSize(true)
        recycler_chat_log.layoutManager = LinearLayoutManager(context)
        recycler_chat_log.itemAnimator = DefaultItemAnimator()

        mChatLog.add(Chat("사진 찍어서 보내줘", TYPE_MY_CHAT))
        mChatLog.add(Chat("싫은데", TYPE_OTHER_CHAT))
        mChatLog.add(Chat(TYPE_TIME_CHAT))
        mChatLog.add(Chat("유감 이구나...", TYPE_MY_CHAT))

        mChatAdapter.notifyItemInserted(mChatLog.size - 1)
        recycler_chat_log.scrollToPosition(mChatAdapter.itemCount - 1)
        /*recycler_chat_log.post {
            mChatAdapter.notifyItemInserted(mChatLog.size - 1)
            //recycler_chat_log.scrollToPosition(mChatAdapter.itemCount - 1)
        }*/
        sendFail(3)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    companion object {
        fun newInstance() = ChatFragment()
    }

    private fun sendFail(position: Int){
        recycler_chat_log.postDelayed({
            if(recycler_chat_log.findViewHolderForAdapterPosition(position) != null){
                recycler_chat_log.findViewHolderForAdapterPosition(position).itemView.find<LinearLayout>(R.id.chat_send_fail).visibility = View.VISIBLE
                recycler_chat_log.findViewHolderForAdapterPosition(position).itemView.find<TextView>(R.id.chat_resend_btn).setOnClickListener {
                    recycler_chat_log.findViewHolderForAdapterPosition(position).itemView.find<LinearLayout>(R.id.chat_send_fail).visibility = View.GONE
                    toast("재전송 했습니다.")
                }
            }
        }, 50)
    }
}
