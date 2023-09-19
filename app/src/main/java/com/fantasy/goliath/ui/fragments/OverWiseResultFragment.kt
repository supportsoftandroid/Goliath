package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager


import com.fantasy.goliath.databinding.FragmentMatchOverWiseResultBinding

import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.ui.adapter.MatchOverTabAdapter
import com.fantasy.goliath.ui.adapter.QuestionAnswerAdapter
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.AddOverViewModel

class OverWiseResultFragment : Fragment() {
    companion object {

        fun newInstance(from: String): OverWiseResultFragment {
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
    lateinit var loginResponse: LoginResponse
    lateinit var preferences: PreferenceManager
    lateinit var utilsManager: UtilsManager
    lateinit var adapter: MatchOverTabAdapter
    lateinit var questionAdapter: QuestionAnswerAdapter
    var dataList = mutableListOf<CommonDataItem>()
    var questionList = mutableListOf<QuestionAnsItem>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = binding.root
        binding.let {
            preferences = PreferenceManager(requireActivity())
            utilsManager = UtilsManager(requireActivity())
            initView()
            clickListener()
        }

        return root
    }

    private fun clickListener() {

        binding.btnBack.setOnClickListener() {

            requireActivity().onBackPressed()

        }


    }


    private fun initView() {
        dataList.clear()

        dataList.add(CommonDataItem("1st Over", "", false))
        dataList.add(CommonDataItem("2nd Over", "", false))
        dataList.add(CommonDataItem("3rd Over", "", false))
        dataList.add(CommonDataItem("4th Over", "", false))
        dataList.add(CommonDataItem("5th Over", "", false))
        dataList.add(CommonDataItem("10th Over", "", false))
        dataList.add(CommonDataItem("15th Over", "", false))

        adapter = MatchOverTabAdapter(
            requireActivity(),
            dataList,
            { pos, type -> onAdapterClick(pos, type) })
        binding.rvOverList.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvOverList.adapter = adapter

        dataList.add(CommonDataItem("Over 1", "", false))
        dataList.add(CommonDataItem("Over 2", "", false))
        dataList.add(CommonDataItem("Over 5", "", false))
        dataList.add(CommonDataItem("Over 10", "", false))
        dataList.add(CommonDataItem("Over 15", "", false))

        questionList.clear()
        questionList.add(QuestionAnsItem("1. End Over EVEN runs.", "No",""))
        questionList.add(QuestionAnsItem("2. First ball scoring?", "Yes",""))
        questionList.add(QuestionAnsItem("3. Boundary", "Yes",""))
        questionList.add(QuestionAnsItem("4. Sixes", "No",""))
        questionList.add(QuestionAnsItem("5. LBW", "No",""))
        questionList.add(QuestionAnsItem("6. Dot Balls LESS than 2", "",""))
        questionList.add(QuestionAnsItem("7. Maiden Over ( 0runs )", "",""))
        questionList.add(QuestionAnsItem("8. Out For a Duck", "No",""))
        questionAdapter = QuestionAnswerAdapter(
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