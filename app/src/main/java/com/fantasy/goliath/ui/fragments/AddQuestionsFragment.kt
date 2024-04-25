package com.fantasy.goliath.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentAddQuestionBinding
import com.fantasy.goliath.databinding.ListOverStatusItemBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.ui.adapter.MatchOverTabAdapter
import com.fantasy.goliath.ui.adapter.QuestionAnswerAdapter
import com.fantasy.goliath.ui.base.BaseFragment

import com.fantasy.goliath.utility.showPredictErrorDialog
import com.fantasy.goliath.viewmodal.AddOverViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.health.kharma.ui.adapters.MyAdapter

class AddQuestionsFragment : BaseFragment() {
    companion object {

        fun newInstance(from: String): AddQuestionsFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = AddQuestionsFragment()
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
    lateinit var questionAdapter: QuestionAnswerAdapter
    var dataList = mutableListOf<CommonDataItem>()
    var questionList = mutableListOf<QuestionAnsItem>()
    var selectedOverPos = 0
    lateinit var overAdapter: MyAdapter<CommonDataItem>

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

            requireActivity().onBackPressed()

        }
        binding.btnSubmit.setOnClickListener() {

            showPredictErrorDialog(requireActivity(),
                { type, dialog -> onPredictCheck(type, dialog) })


        }


    }

    private fun onPredictCheck(type: String, dialog: BottomSheetDialog) {
        dialog.dismiss()
        addFragmentToBackStack(

            OverQuestionStatusFragment.newInstance("add")
        )
    }


    private fun initView() {
        binding.clvQuestion.visibility = View.GONE
        dataList.clear()

        dataList.add(CommonDataItem("1", "", false))
        dataList.add(CommonDataItem("2", "", false))
        dataList.add(CommonDataItem("3", "", false))
        dataList.add(CommonDataItem("4", "", false))
        dataList.add(CommonDataItem("5", "", false))
        dataList.add(CommonDataItem("10", "", false))
        dataList.add(CommonDataItem("12", "", false))
        dataList.add(CommonDataItem("15  ", "", false))


        overAdapter = MyAdapter(
            R.layout.list_over_row_item,
            dataList
        ) { view, data, pos ->

            ListOverStatusItemBinding.bind(view).apply {
                tvTitle.text = "Over Number: ${data.title}"
                clvCardMain.setOnClickListener {
                    selectedOverPos = pos

                    overAdapter.notifyDataSetChanged()
                }
                if (pos == selectedOverPos) {
                    tvTitle.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.colorWhite
                        )
                    )

                    clvCardMain.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.app_color
                            )
                        )
                } else {
                    tvTitle.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.app_color
                        )
                    )
                    clvCardMain.setBackgroundResource(R.drawable.border_layout_result_blue_radius_5)

                }
            }
        }

        binding.rvOverList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvOverList.adapter = overAdapter

        questionList.clear()
        questionList.add(QuestionAnsItem("1. End Over EVEN runs.", "", ""))
        questionList.add(QuestionAnsItem("2. First ball scoring?", "", ""))
        questionList.add(QuestionAnsItem("3. Boundary", "", ""))
        questionList.add(QuestionAnsItem("4. Sixes", "", ""))
        questionList.add(QuestionAnsItem("5. LBW", "", ""))
        questionList.add(QuestionAnsItem("6. Dot Balls LESS than 2", "", ""))
        questionList.add(QuestionAnsItem("7. Maiden Over ( 0runs )", "", ""))
        questionList.add(QuestionAnsItem("8. Out For a Duck", "", ""))
        questionAdapter = QuestionAnswerAdapter(
            requireActivity(),
            questionList,
            { pos, type -> onQuestionAdapterClick(pos, type) })
        binding.rvQuestionList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvQuestionList.adapter = questionAdapter


    }

    private fun onAdapterClick(pos: Int, type: String) {

    }

    private fun onQuestionAdapterClick(pos: Int, type: String) {

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

    override fun onDestroyView() {
        super.onDestroyView()

    }
}