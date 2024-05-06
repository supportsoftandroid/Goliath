package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.databinding.FragmentAddQuestionBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.OverItem
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.ui.adapter.MatchOverTabAdapter
import com.fantasy.goliath.ui.adapter.QuestionAnswerStatusAdapter
import com.fantasy.goliath.ui.base.BaseFragment

import com.fantasy.goliath.viewmodal.AddOverViewModel

class OverQuestionStatusFragment : BaseFragment() {
    companion object {

        fun newInstance(from: String): OverQuestionStatusFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = OverQuestionStatusFragment()
            fragment.arguments = args
            return fragment
        }


    }

    private val viewModal by lazy { ViewModelProvider(this)[AddOverViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentAddQuestionBinding.inflate(
            layoutInflater
        )
    }
    lateinit var loginResponse: LoginResponse


    lateinit var adapter: MatchOverTabAdapter
    lateinit var questionAdapter: QuestionAnswerStatusAdapter
    var dataList = arrayListOf<OverItem>()
    var questionList = arrayListOf<QuestionAnsItem>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {

            initView()
            clickListener()
        }

        return binding.root
    }

    private fun clickListener() {
        binding.viewHeader.setClickListener(this)

        binding.btnSubmit.setOnClickListener() {

           addFragmentToBackStack(

                MatchOverStatusFragment.newInstance("add")
            )

        }


    }


    private fun initView() {
        dataList.clear()

        dataList.add(OverItem("", "Over 1", "", false))
        dataList.add(OverItem("", "Over 2", "", false))
        dataList.add(OverItem("", "Over 5", "", false))
        dataList.add(OverItem("", "Over 10", "", false))
        dataList.add(OverItem("", "Over 15", "", false))

        adapter = MatchOverTabAdapter(
            requireActivity(),
            dataList,
            { pos, type -> onAdapterClick(pos, type) })
        binding.rvOverList.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvOverList.adapter = adapter



        with(questionList) {
            clear()
            add(QuestionAnsItem("","","End Over EVEN runs.", "No","Yes"))
            add(QuestionAnsItem("","","First ball scoring?", "Yes","No"))
            add(QuestionAnsItem("","","Boundary", "No","Yes"))
            add(QuestionAnsItem("","","Sixes", "No","No"))
            add(QuestionAnsItem("","","LBW", "No","Yes"))
            add(QuestionAnsItem("","","Dot Balls LESS than 2", "Yes","Yes"))
            add(QuestionAnsItem("","","Maiden Over ( 0runs )", "Yes","No"))
            add(QuestionAnsItem("","","Out For a Duck", "No","Yes"))
            questionAdapter = QuestionAnswerStatusAdapter(
                requireActivity(),
                this,
                { pos, type -> onQuestionAdapterClick(pos, type) })
        }
        binding .rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding. rvList.adapter = questionAdapter


    }

    private fun onQuestionAdapterClick(pos: Int, type: String) {

    }

    private fun onAdapterClick(pos: Int, type: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()

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
}