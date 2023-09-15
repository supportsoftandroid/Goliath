package com.fantasy.goliath.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ActivityStaticPagesBinding

import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.StaticViewModal


class StaticPagesActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        fun newInstance(mContext: Context, title: String, type: String) {
            val intent = Intent(mContext, StaticPagesActivity::class.java)
            intent.putExtra(PAGE_Title, title)
            intent.putExtra(PAGE_TYPE, type)
            (mContext as Activity).startActivity(intent)
        }

        var PAGE_TYPE: String = "page_type"
        var PAGE_Title: String = "page_title"
    }

    private lateinit var binding: ActivityStaticPagesBinding



    lateinit var viewModal: StaticViewModal
    private lateinit var utilsManager: UtilsManager
    var type: String = ""
    var title: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaticPagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModal = ViewModelProvider(this).get(StaticViewModal::class.java)
        type = intent.getStringExtra(PAGE_TYPE).toString()
        title = intent.getStringExtra(PAGE_Title).toString()
        utilsManager = UtilsManager(this)

        initViews()


    }

    /*private fun getApiData() {
         if (utilsManager.isNetworkConnected()) {
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
                     StaticData.printLog(title, res.toString())
                     if (res.status) {
                         loadData(res.data)
                     }
                 })
             }
         } else utilsManager.showAlertConnectionError()


    }
*/
    private fun initViews() {
        binding.viewHeader.imgBack.setOnClickListener(this)
        binding.viewHeader.txtTitle.text = title

            binding.webView.visibility = View.VISIBLE
            binding.rvList.visibility = View.GONE
            loadData(resources.getString(R.string.dummy_description))
        }





    private fun loadData(content: String) {
        binding.webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);

    }

    override fun onClick(v: View?) {
        finish()
    }

}
