package com.bliends.pc.bliends.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.R.layout.*
import com.bliends.pc.bliends.data.Chat
import com.bliends.pc.bliends.data.Chat.Companion.TYPE_MY_CHAT
import com.bliends.pc.bliends.data.Chat.Companion.TYPE_OTHER_CHAT
import com.bliends.pc.bliends.data.Chat.Companion.TYPE_TIME_CHAT

class ChatLogAdapter(private var mChatLog : ArrayList<Chat>) : RecyclerView.Adapter<ChatLogAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.chatText.text = mChatLog[position].message
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = -1
        when(viewType){
            TYPE_MY_CHAT-> layout = chat_my_content_item
            TYPE_OTHER_CHAT-> layout = chat_other_content_item
            TYPE_TIME_CHAT-> layout = chat_log_time_item
        }
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int = mChatLog[position].type

    override fun getItemCount(): Int = mChatLog.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var chatText: TextView = itemView!!.findViewById(R.id.chat_log)
        val chatResend: TextView? = if (itemViewType == TYPE_OTHER_CHAT) itemView!!.findViewById(R.id.chat_resend_btn) else null
        val chatSendFail: LinearLayout? = if(itemViewType == TYPE_MY_CHAT) itemView!!.findViewById(R.id.chat_send_fail) else null
    }
}