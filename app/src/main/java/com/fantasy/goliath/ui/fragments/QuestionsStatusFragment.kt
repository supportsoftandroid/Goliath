package com.fantasy.goliath.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentAddQuestionBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.model.OverItem
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.ui.adapter.MatchOverTabAdapter
import com.fantasy.goliath.ui.adapter.QuestionAnswerStatusAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.viewmodal.QuestionsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject

class QuestionsStatusFragment : BaseFragment() {
    companion object {

        fun newInstance(
            from: String,
            over_id: String,
            over_name: String,
            matchItem: MatchItem
        ): QuestionsStatusFragment {
            val args = Bundle()
            args.putString("from", from)
            args.putString("over_id", over_id)
            args.putString("over_name", over_name)
            args.putSerializable("match_item", matchItem)
            val fragment = QuestionsStatusFragment()
            fragment.arguments = args
            return fragment
        }


    }

    private val viewModal by lazy { ViewModelProvider(this)[QuestionsViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentAddQuestionBinding.inflate(
            layoutInflater
        )
    }
    lateinit var loginResponse: LoginResponse


    lateinit var adapter: MatchOverTabAdapter
    lateinit var questionAdapter: QuestionAnswerStatusAdapter

    var questionList = arrayListOf<QuestionAnsItem>()
    var selectedOverPos = 0

    lateinit var matchItem: MatchItem
    lateinit var overItem: OverItem
    lateinit var match_id: String
    var over_name = ""
    var over_id = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {
            over_id = requireArguments().getString("over_id")!!
            over_name = requireArguments().getString("over_name")!!
            matchItem = arguments?.getSerializable("match_item") as MatchItem
            match_id = matchItem.match_id


            initView()
            clickListener()
            callQuestionAPI()
        }

        return binding.root
    }

    private fun clickListener() {

        binding.viewHeader.setClickListener(this)
        binding.btnSubmit.setOnClickListener() {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finishAffinity()


            /*showPredictErrorDialog(requireActivity(),
                { type, dialog -> onPredictCheck(type, dialog) })*/


        }


    }

    private fun onPredictCheck(type: String, dialog: BottomSheetDialog) {
        dialog.dismiss()
        addFragmentToBackStack(

            QuestionsStatusFragment.newInstance("add", over_id, over_name, matchItem)
        )

    }


    private fun initView() {

        binding.clvQuestion.isVisible = false
        binding.tvOverName.text = "Over Number: ${over_name}"
        loadImage(matchItem.teama.logo_url, binding.viewHeader.getToolBarView().imgTeam1)
        loadImage(matchItem.teamb.logo_url, binding.viewHeader.getToolBarView().imgTeam2)
        binding.viewHeader.setTitle("${matchItem.short_title}  ")
        binding.clvMatchCard.tvLeft.text = "${matchItem.teama.short_name}"
        binding.clvMatchCard.tvRight.text = "${matchItem.teamb.short_name}"
        binding.clvMatchCard.tvLeftFullName.text = "${matchItem.teama.name}"
        binding.clvMatchCard.tvRightFullName.text = "${matchItem.teamb.name}"
        loadImage(matchItem.teama.logo_url, binding.clvMatchCard.imgLeft)
        loadImage(matchItem.teamb.logo_url, binding.clvMatchCard.imgRight)
        binding.btnSubmit.text= getString(R.string.confirm_pay)



        questionAdapter = QuestionAnswerStatusAdapter(
            requireActivity(),
            questionList,
            { pos, type -> onQuestionAdapterClick(pos, type) })
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = questionAdapter


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

    private fun callQuestionAPI() {

        if (utilsManager.isNetworkConnected()) {

            val json = JsonObject()
            json.addProperty("match_id", match_id)
            json.addProperty("over_id", over_id)
            viewModal.getQuestions(
                requireActivity(), preferenceManager.getAuthToken(), json
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                questionList.clear()
                if (res.status) {
                    matchItem = res.data.matchdetail
                    questionList.addAll(matchItem.question)

                } else {
                    showErrorToast(res.message)
                }
                setUIData(res.message)

            })
        }


    }

    private fun setUIData(message: String) {

        questionAdapter.notifyDataSetChanged()

        if (questionList.isEmpty()) {
            binding.tvMessageLoading.isVisible = true
            binding.tvMessageLoading.text = message
        } else {
            binding.tvMessageLoading.isVisible = false
            binding.btnSubmit.isVisible = true

        }
    }
}