package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentTransactionHistoryBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.TransactionsItem
import com.fantasy.goliath.ui.adapter.TransactionAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.HomeViewModel
import com.fantasy.goliath.viewmodal.TransactionsViewModel
import com.google.gson.JsonObject

class TransactionHistoryFragment : BaseFragment() {

    companion object {
        fun newInstance(from: String): TransactionHistoryFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = TransactionHistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModal by lazy { ViewModelProvider(this)[TransactionsViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentTransactionHistoryBinding.inflate(layoutInflater)
    }
    lateinit var loginResponse: LoginResponse

    lateinit var adapter: TransactionAdapter
    var dataList = arrayListOf<TransactionsItem>()
    var type = "deposit"//deposit,withdrawal,prediction,winning

    var isLoading = false
    var currentPage = 1
    var selectedPos = -1
    var totalPage = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {

            initView()
            clickListener()
            callListAPI()
        }


        return binding.root
    }

    private fun clickListener() {
        binding.viewHeader.getToolBarView().imgMenu1.isVisible = false
        binding.viewHeader.getToolBarView().imgMenu2.isVisible = false
        binding.viewHeader.setClickListener(this)

    }

    private fun initView() {
        binding.viewHeader.setTitle(requireActivity().getString(R.string.transaction_history))
        dataList.clear()
        binding.viewBody.tvMessage.isVisible = false


        adapter = TransactionAdapter(
            requireActivity(),
            dataList,
            { pos, type -> onAdapterClick(pos, type) })
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.viewBody.rvList.layoutManager = layoutManager
        binding.viewBody.rvList.adapter = adapter
        type = "deposit"

        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            //green
            updateGreenTab(binding.rbDeposit)
            updateGreenTab(binding.rbWinning)
            //red
            updateRedTab(binding.rbWithdraw)
            updateRedTab(binding.rbTotalFeePaid)
            when (checkedId) {
                R.id.rbDeposit -> {
                    type = "deposit"
                }

                R.id.rbWithdraw -> {

                    type = "withdrawal"
                }

                R.id.rbTotalFeePaid -> {
                    type = "prediction"
                }

                R.id.rbWinning -> {
                    type = "winning"
                }
            }
            currentPage = 1
            dataList.clear()
            binding.viewBody.tvMessage.isVisible = true
            binding.viewBody.tvMessage.text = getString(R.string.loading)
            callListAPI()

        }

        binding.viewBody.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.getChildCount()
                val totalItemCount: Int = layoutManager.getItemCount()
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                    if (!isLoading &&!TextUtils.isEmpty(totalPage)) {
                        callListAPI()
                    }
                }

            }
        })
    }

    fun updateGreenTab(radioButton: RadioButton) {
        if (radioButton.isChecked) {
            radioButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorWhite))
        } else {
            radioButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.color2EB100))
        }
    }

    fun updateRedTab(radioButton: RadioButton) {
        if (radioButton.isChecked) {
            radioButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorWhite))
        } else {
            radioButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorRed))
        }
    }

    private fun onAdapterClick(pos: Int, type: String) {
        //MainActivity.hideNavigationTab()
        /* UiUtils.backStackAddFragment(
             requireActivity(),
             AddOverFragment.newInstance("add")
         )*/
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onNotificationsIconClick() {
        super.onNotificationsIconClick()
    }

    override fun onWalletIconClick() {
        super.onWalletIconClick()
    }


    private fun callListAPI() {

        if (utilsManager.isNetworkConnected()) {
            isLoading = true
            val json = JsonObject()
            json.addProperty("type", type)


            viewModal.getTransactionsList(
                requireActivity(), preferenceManager.getAuthToken(), currentPage, json
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->

                if (res.status) {
                    dataList.addAll(res.data.transaction.data)
                    currentPage++
                }


                updateUI(res.message)
                totalPage = res.data.transaction.next_page_url
                isLoading = false
            })
        }else{
            updateUI(getString(R.string.no_internet_connection_please_try_again))
        }


    }

    private fun updateUI(message: String) {
        adapter.notifyDataSetChanged()
        if (dataList.isEmpty()) {
            binding.viewBody.tvMessage.isVisible = true
            binding.viewBody.tvMessage.text = message
        } else {
            binding.viewBody.tvMessage.isVisible = false
        }
    }
}