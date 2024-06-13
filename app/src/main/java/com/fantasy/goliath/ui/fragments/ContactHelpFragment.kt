package com.fantasy.goliath.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ActivityStaticPagesBinding
import com.fantasy.goliath.databinding.FragmentHelpContactBinding


import com.fantasy.goliath.ui.base.BaseFragment


import com.fantasy.goliath.viewmodal.StaticViewModal


class ContactHelpFragment : BaseFragment() {
    companion object {
        fun newInstance(title: String, type: String): ContactHelpFragment {
            val args = Bundle()
            args.putString(PAGE_Title, title)
            args.putString(PAGE_TYPE, type)

            val fragment = ContactHelpFragment()
            fragment.arguments = args
            return fragment
        }


        var PAGE_TYPE: String = "page_type"
        var PAGE_Title: String = "page_title"
    }


    private val binding by lazy { FragmentHelpContactBinding.inflate(layoutInflater) }

    private val viewModal by lazy { ViewModelProvider(this)[StaticViewModal::class.java] }


    var type: String = ""
    var strTitle: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)


        type = arguments?.getString(PAGE_TYPE).toString()
        strTitle = arguments?.getString(PAGE_Title).toString()


        initViews()


        return binding.root


    }

     private fun callContactAPI() {
        /* if (utilsManager.isNetworkConnected()) {
             if(type.equals("FAQs")){
                 viewModal.getFaqAPI(this, type).observe(this, Observer { res ->
                   if (res.status) {
                       dataList.addAll(res.data)
                       binding.rvList.visibility=View.VISIBLE
                       loadFaqData()
                   }
                 })
             }else {
                 viewModal.getCallAPI(this, type).observe(this, Observer { res ->
                     UiUtils.printLog(title, res.toString())
                     if (res.status) {
                         loadData(res.data)
                     }
                 })
             }
         } else utilsManager.showAlertConnectionError()

*/
    }

    private fun initViews() {
        binding.viewHeader.setClickListener(this)
        binding.viewHeader.setTitle(strTitle)


    }


    private fun loadData(content: String) {


    }

    override fun onWalletIconClick() {
        super.onWalletIconClick()
    }

    override fun onNotificationsIconClick() {
        super.onNotificationsIconClick()
    }


}
