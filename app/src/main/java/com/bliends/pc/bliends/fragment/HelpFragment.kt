package com.bliends.pc.bliends.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bliends.pc.bliends.R
import com.bliends.pc.bliends.adapter.HelpAdapter
import com.bliends.pc.bliends.data.Help
import com.bliends.pc.bliends.data.Sign
import com.bliends.pc.bliends.util.ORMUtil
import com.bliends.pc.bliends.util.RetrofitRes
import com.bliends.pc.bliends.util.RetrofitUtil
import com.bliends.pc.bliends.util.TTSUtil
import kotlinx.android.synthetic.main.fragment_help.*
import org.jetbrains.anko.support.v4.toast

class HelpFragment : Fragment(), View.OnClickListener{

    private var mLog = ArrayList<Help>()
    private lateinit var mHelpAdapter : HelpAdapter

    //private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNTM3NzA0NTUxfQ.fegceqw-hj0XK5iBrgBAiOoabcd1EJZUb3zwYkHOSkA"
    private var token : String? = null
    //private val token_2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNTM3NzA0NTI0fQ.RSqPYZfHMIElw3DpszZu3G5_5VY9DFpEwqthnIyD85M"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mLayoutManager = LinearLayoutManager(context)
/*        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true*/

        mHelpAdapter = HelpAdapter(mLog)
        recycle_help_log.adapter = mHelpAdapter
        recycle_help_log.layoutManager = mLayoutManager
        recycle_help_log.setHasFixedSize(false)
        recycle_help_log.itemAnimator = DefaultItemAnimator()

        floating_call.setOnClickListener(this)

        val list = ORMUtil(context!!).tokenORM.find(Sign())
        val sign = list[list.size - 1] as Sign
        token = sign.token
        if(token == null) toast("토큰값이 없습니다!")

        RetrofitUtil.postService.getHelpList(token!!).enqueue(object : RetrofitRes<ArrayList<Help>>(context!!){
            override fun callback(code: Int, body: ArrayList<Help>?) {
                Log.e("Help res code", code.toString())
                if(code == 200) {
                    body!!.forEach {
                        mHelpAdapter.add(it)
                    }
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_help, container, false)

    companion object { fun newInstance() = HelpFragment() }

    override fun onClick(v: View?) {
        val dialogFragment = CallDialogFragment()
        dialogFragment.isCancelable = false
        dialogFragment.show(activity!!.fragmentManager, "call_dialog")
    }
/*        RetrofitUtil.postService.postHelp(token_2, 36.3728748F, 127.4279327F, "M", null)
                .enqueue(object : RetrofitRes<Help>(context!!){
                    override fun callback(code: Int, body: Help?) {
                        Log.e("Activity Post res code", code.toString())
                        Log.e("Activty_id", body!!.id.toString())
                    }
                })*/
}
