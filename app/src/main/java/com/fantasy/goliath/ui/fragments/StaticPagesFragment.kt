package com.fantasy.goliath.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ActivityStaticPagesBinding


import com.fantasy.goliath.ui.base.BaseFragment


import com.fantasy.goliath.viewmodal.StaticViewModal


class StaticPagesFragment : BaseFragment() {
    companion object {
        fun newInstance(title: String, type: String): StaticPagesFragment {
            val args = Bundle()
            args.putString(PAGE_Title, title)
            args.putString(PAGE_TYPE, type)

            val fragment = StaticPagesFragment()
            fragment.arguments = args
            return fragment
        }


        var PAGE_TYPE: String = "page_type"
        var PAGE_Title: String = "page_title"
    }


    private val binding by lazy { ActivityStaticPagesBinding.inflate(layoutInflater) }

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
        getApiData()

        return binding.root


    }

    private fun getApiData() {
         if (utilsManager.isNetworkConnected()) {

                 viewModal.getAppContent(requireActivity(), type).observe(requireActivity(), Observer { res ->

                     if (res.status) {
                         loadData(res.data.value)
                     }else{
                         showErrorToast(res.message)
                     }
                 })

         } else utilsManager.showAlertConnectionError()


    }

    private fun initViews() {
        binding.viewHeader.setClickListener(this)
        binding.viewHeader.setTitle(strTitle)

        binding.webView.visibility = View.VISIBLE
        binding.rvList.visibility = View.GONE
        loadData(resources.getString(R.string.loading))
    }


    private fun loadData(content: String) {
        binding.webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);

    }

    override fun onWalletIconClick() {
        super.onWalletIconClick()
    }

    override fun onNotificationsIconClick() {
        super.onNotificationsIconClick()
    }


}
