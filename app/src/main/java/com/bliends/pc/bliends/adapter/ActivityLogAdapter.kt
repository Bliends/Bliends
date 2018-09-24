package com.bliends.pc.bliends.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.data.ActivityLog
import com.bliends.pc.bliends.util.AddMarkerUtil
import com.bliends.pc.bliends.util.DateStringUtil
import kotlinx.android.synthetic.main.location_log_item.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import java.text.DecimalFormat

class ActivityLogAdapter(private val behavior: BottomSheetBehavior<LinearLayout>,
                         private var mActivityLog: ArrayList<ActivityLog>) :
        RecyclerView.Adapter<ActivityLogAdapter.ViewHolder>() {

    private lateinit var view : View

    fun add(a: ActivityLog){
        mActivityLog.add(a)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if((mActivityLog[holder.adapterPosition].label == null)) {
            holder.itemView.message_text.text = AddMarkerUtil.getAdress(
                    mActivityLog[holder.adapterPosition].latitude,
                    mActivityLog[holder.adapterPosition].longitude)!!.getAddressLine(0) + " 에서 지폐 인식"

            holder.itemView.money_text.text =
                    DecimalFormat("#,###").format(mActivityLog[holder.adapterPosition].payments) + "원"
        }else{
            holder.itemView.message_text.text = mActivityLog[holder.adapterPosition].label!!.name + " 도착"
            holder.itemView.money_text.textColor = Color.WHITE
        }

        holder.itemView.time_text.text = DateStringUtil.replaceDate(mActivityLog[holder.adapterPosition].createdAt!!)

        view.setOnClickListener {
            if(mActivityLog[holder.adapterPosition].label == null) {
                AddMarkerUtil.addWad(
                        mActivityLog[holder.adapterPosition].latitude!!.toDouble(),
                        mActivityLog[holder.adapterPosition].longitude!!.toDouble(),
                        AddMarkerUtil.getAdress(
                                mActivityLog[holder.adapterPosition].latitude,
                                mActivityLog[holder.adapterPosition].longitude)!!.getAddressLine(0) + " 에서 지폐 인식")

            }else{
                AddMarkerUtil.addWad(
                        mActivityLog[holder.adapterPosition].label!!.latitude!!.toDouble(),
                        mActivityLog[holder.adapterPosition].label!!.longitude!!.toDouble(),
                        mActivityLog[holder.adapterPosition].label!!.name!!)
            }
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.location_log_item, parent, false)
        view = v.rootView.find(R.id.location_log_item_layout)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mActivityLog.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

}