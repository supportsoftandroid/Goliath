package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.databinding.FragmentAddPredictionBinding
import com.fantasy.goliath.databinding.FragmentMatchPredictionStatusBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.ui.adapter.MatchOverTabAdapter
import com.fantasy.goliath.ui.adapter.QuestionAnswerAdapter
import com.fantasy.goliath.ui.adapter.SelectedOverAdapter
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.StaticData
import com.fantasy.goliath.utility.StaticData.Companion.showToast
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.AddPredictionViewModel

class AddQuestionsFragment : Fragment() {
    companion object {

        fun newInstance(from: String ): AddQuestionsFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = AddQuestionsFragment()
            fragment.arguments = args
            return fragment
        }


    }
    private val viewModal by lazy { ViewModelProvider(this)[AddPredictionViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) { FragmentMatchPredictionStatusBinding.inflate(layoutInflater) }
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
            preferences=PreferenceManager(requireActivity())
            utilsManager=UtilsManager(requireActivity())
            initView()
            clickListener()
        }

        return root
    }
    private fun clickListener() {

        binding.btnBack.setOnClickListener() {

          requireActivity().onBackPressed()

        }
        binding.btnSubmit.setOnClickListener() {

            StaticData.backStackAddFragment(
                requireActivity(),
                MatchStatusQuestionsFragment.newInstance("add")
            )
        }


    }



    private fun initView() {
        dataList.clear()

        dataList.add(CommonDataItem("Over 1", "",false))
        dataList.add(CommonDataItem("Over 2", "",false))
        dataList.add(CommonDataItem("Over 5", "",false))
        dataList.add(CommonDataItem("Over 10", "",false))
        dataList.add(CommonDataItem("Over 15", "",false))


        adapter = MatchOverTabAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.rvOverList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvOverList.adapter = adapter

        questionList.clear()
        questionList.add(QuestionAnsItem("1. End Over EVEN runs.", "",""))
        questionList.add(QuestionAnsItem("2. First ball scoring?", "",""))
        questionList.add(QuestionAnsItem("3. Boundary", "",""))
        questionList.add(QuestionAnsItem("4. Sixes", "",""))
        questionList.add(QuestionAnsItem("5. LBW", "",""))
        questionList.add(QuestionAnsItem("6. Dot Balls LESS than 2", "",""))
        questionList.add(QuestionAnsItem("7. Maiden Over ( 0runs )", "",""))
        questionList.add(QuestionAnsItem("8. Out For a Duck", "",""))
        questionAdapter = QuestionAnswerAdapter(requireActivity(), questionList, { pos, type -> onQuestionAdapterClick(pos, type) })
        binding.rvQuestionList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvQuestionList.adapter = questionAdapter



    }

    private fun onAdapterClick(pos: Int, type: String) {

    }
    private fun onQuestionAdapterClick(pos: Int, type: String) {

    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
}