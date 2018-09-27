package com.bliends.pc.bliends.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.activity.LoginActivity
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.data.User
import com.bliends.pc.bliends.util.ORMUtil
import kotlinx.android.synthetic.main.fragment_setting.*
import org.jetbrains.anko.support.v4.startActivity


class SettingFragment : Fragment(), View.OnClickListener{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_label_settings.setOnClickListener(this)
        change_phone_settings.setOnClickListener(this)
        change_password_settings.setOnClickListener(this)
        logout_settings.setOnClickListener(this)

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
                val changePhoneFragment = ChangePhoneDialogFragment("Protector")
                changePhoneFragment.show(activity!!.fragmentManager, "add_dialog")
            }
            R.id.change_password_settings->{
                val changePasswordFragment = ChangePasswordDialogFragment("Protector")
                changePasswordFragment.show(activity!!.fragmentManager, "add_dialog")
            }
            R.id.logout_settings->{
                ORMUtil(context!!).tokenORM.delete(Sign())
                ORMUtil(context!!).userORM.delete(User())
                startActivity<LoginActivity>()
                activity!!.finish()
            }
        }
    }
}
