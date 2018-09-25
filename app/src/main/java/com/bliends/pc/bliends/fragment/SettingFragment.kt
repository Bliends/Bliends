package com.bliends.pc.bliends.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment(), View.OnClickListener{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_label_settings.setOnClickListener(this)
        change_phone_settings.setOnClickListener(this)
        val pref = context?.getSharedPreferences("notificationSwitch", Context.MODE_PRIVATE)
        switch_notification_settings.isChecked = pref!!.getBoolean("notificationSwitch", true)
        switch_notification_settings.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                with(pref.edit()) {
                    putBoolean("notificationSwitch", true)
                    apply()
                }
            }else{
                with(pref.edit()) {
                    putBoolean("notificationSwitch", false)
                    apply()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.add_label_settings->{
                val addLabelFragment = AddLabelDialogFragment()
                addLabelFragment.isCancelable = false
                addLabelFragment.show(activity!!.fragmentManager, "add_dialog")
            }
            R.id.change_phone_settings->{
                val changePhoneFragment = ChangePhoneDialogFragment()
                changePhoneFragment.show(activity!!.fragmentManager, "add_dialog")
            }
        }
    }
}
