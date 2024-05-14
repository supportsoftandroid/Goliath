package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager


import com.fantasy.goliath.databinding.FragmentMatchOverWiseResultBinding


import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.model.OverItem
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.ui.adapter.MatchOverTabAdapter

import com.fantasy.goliath.ui.adapter.QuestionAnswerStatusAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.viewmodal.AddOverViewModel

class OverWiseResultFragment : BaseFragment() {
    companion object {

        fun newInstance(from: String, matchItem: MatchItem): OverWiseResultFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = OverWiseResultFragment()
            fragment.arguments = args
            return fragment
        }


    }

    private val viewModal by lazy { ViewModelProvider(this)[AddOverViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMatchOverWiseResultBinding.inflate(
            layoutInflater
        )
    }


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

        binding.btnBack.setOnClickListener() {

            onBackPressed()

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



        questionList.clear()
        questionList.add(QuestionAnsItem("","","1. End Over EVEN runs.", "No","Yes"))
        questionList.add(QuestionAnsItem("","","2. First ball scoring?", "Yes","No"))
        questionList.add(QuestionAnsItem("","","3. Boundary", "No","Yes"))
        questionList.add(QuestionAnsItem("","","4. Sixes", "No","No"))
        questionList.add(QuestionAnsItem("","","5. LBW", "No","Yes"))
        questionList.add(QuestionAnsItem("","","6. Dot Balls LESS than 2", "Yes","Yes"))
        questionList.add(QuestionAnsItem("","","7. Maiden Over ( 0runs )", "Yes","No"))
        questionList.add(QuestionAnsItem("","","8. Out For a Duck", "No","Yes"))
        questionAdapter = QuestionAnswerStatusAdapter(
            requireActivity(),
            questionList,
            { pos, type -> onQuestionAdapterClick(pos, type) })
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = questionAdapter


    }

    private fun onQuestionAdapterClick(pos: Int, type: String) {

    }

    private fun onAdapterClick(pos: Int, type: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}