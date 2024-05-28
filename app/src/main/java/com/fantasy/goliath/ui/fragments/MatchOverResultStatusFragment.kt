package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.FragmentMatchPredictionStatusBinding
import com.fantasy.goliath.model.InningItem

import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.model.OverItem
import com.fantasy.goliath.model.OverResultData
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.ui.adapter.InningTabAdapter
import com.fantasy.goliath.ui.adapter.MatchOverTabAdapter
import com.fantasy.goliath.ui.adapter.QuestionAnswerStatusAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.printLog
import com.fantasy.goliath.utility.setMatchCardUIData

import com.fantasy.goliath.viewmodal.MatchOverResultViewModel
import com.google.gson.JsonObject

class MatchOverResultStatusFragment : BaseFragment() {
    companion object {
        fun newInstance(from: String, matchItem: MatchItem): MatchOverResultStatusFragment {
            val args = Bundle()
            args.putString("from", from)
            args.putSerializable("match_item", matchItem)
            val fragment = MatchOverResultStatusFragment()
            fragment.arguments = args
            return fragment
        }


    }

    private val viewModal by lazy { ViewModelProvider(this)[MatchOverResultViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMatchPredictionStatusBinding.inflate(
            layoutInflater
        )
    }


    lateinit var inningsAdapter: InningTabAdapter
    lateinit var overAdapter: MatchOverTabAdapter
    lateinit var questionAdapter: QuestionAnswerStatusAdapter
    var inningsList = arrayListOf<InningItem>()
    var overList = arrayListOf<OverItem>()
    var questionList = arrayListOf<QuestionAnsItem>()

    lateinit var matchItem: MatchItem
    lateinit var overResultData: OverResultData
    lateinit var overItem: OverItem
    lateinit var match_id: String
    var over_id = ""
    var selectedInningPos=-1
    var selectedOverPos=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.let {
            matchItem = arguments?.getSerializable("match_item") as MatchItem
            match_id = matchItem.match_id
            initView()
            clickListener()
            callMatchDetailsAPI()
        }

        return binding.root
    }

    private fun clickListener() {
        binding.viewHeader.setClickListener(this)

        binding.btnSubmit.setOnClickListener() {

            addFragmentToBackStack(
                OverWiseResultFragment.newInstance("add",matchItem)
            )

        }

    }


    private fun initView() {
        overList.clear()


        //  overList.add(OverItem("","1st Over", "", false))
        binding.viewHeader.setTitle("${matchItem.short_title}  ")
        loadImage(matchItem.teama.logo_url, binding.viewHeader.getToolBarView().imgTeam1)
        loadImage(matchItem.teamb.logo_url, binding.viewHeader.getToolBarView().imgTeam2)

        setMatchDataUI()

        inningsAdapter = InningTabAdapter(requireActivity(), inningsList, { pos, type ->
            selectedInningPos=pos
            inningsAdapter.notifyDataSetChanged()
            overList.clear()
            questionList.clear()
            questionAdapter.notifyDataSetChanged()
            binding.clvResultBox.isVisible = false
            binding.clvYourPrediction.isVisible = false
            binding.tvMessage.isVisible = true
            binding.tvMessage.text = requireActivity().getString(R.string.loading)

            if (inningsList[pos].overs.size > 0) {
                overList.addAll(inningsList[pos].overs)
                overItem = overList[0]
                over_id = overItem.over_id
                overAdapter.notifyDataSetChanged()
               // updateQuestionResultUI()
                callOverResultAPI()
            } else {

                updateQuestionResultUI()
            }


        })


       val layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvInningsList.layoutManager = layoutManager
        binding.rvInningsList.adapter = inningsAdapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            callMatchDetailsAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.rvInningsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                binding.swipeRefreshLayout.isEnabled = firstVisibleItemPosition == 0


            }
        })
        overAdapter = MatchOverTabAdapter(
            requireActivity(),
            overList,
            { pos, type -> onOverAdapterClick(pos, type) })
        binding.rvOverList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvOverList.adapter = overAdapter
        questionList.clear()
        questionAdapter = QuestionAnswerStatusAdapter(
            requireActivity(),
            questionList,
            { pos, type ->

            })
        binding.rvQuestionList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvQuestionList.adapter = questionAdapter
        binding.rvQuestionList.isNestedScrollingEnabled = false

    }


    private fun setMatchDataUI() {
        setMatchCardUIData(binding.clvMatchCard,matchItem)
    }


    private fun onOverAdapterClick(pos: Int, type: String) {
        selectedOverPos=pos
        overItem = overList[selectedOverPos]
        over_id = overItem.over_id
        callOverResultAPI()

    }

    private fun callMatchDetailsAPI() {

        if (utilsManager.isNetworkConnected()) {

            val json = JsonObject()
            json.addProperty("match_id", match_id)
            viewModal.getMatchesDetails(
                requireActivity(), preferenceManager.getAuthToken(), json
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                if (res.status) {
                    matchItem=res.data.matchdetail
                    setMatchDataUI()
                    overList.clear()
                    questionList.clear()
                    inningsList.clear()
                    matchItem = res.data.matchdetail
                    for (i in res.data.matchdetail.innings){
                        if (i.overs.size>0){
                            inningsList.add(i)
                        }
                    }
                  // inningsList.addAll(res.data.matchdetail.innings)
                    if (inningsList.size>0){
                        if (selectedInningPos!=-1){
                            overList.addAll(inningsList[selectedInningPos].overs)
                        }else{
                            selectedInningPos=0
                            overList.addAll(inningsList[0].overs)
                        }

                    }

                    if (overList.size > 0) {
                        overItem = overList[selectedOverPos]
                        over_id = overItem.over_id
                        updateQuestionResultUI()
                        callOverResultAPI()
                    } else {
                        updateQuestionResultUI()

                    }

                } else {
                    showErrorToast(res.message)
                    updateQuestionResultUI()
                }
                inningsAdapter.notifyDataSetChanged()
                printLog("inningsList", inningsList.size.toString())
                //setUIData(res.message)

            })
        }


    }

    private fun updateQuestionResultUI() {
        binding.tvMessage.isVisible=overList.isEmpty()
        binding.tvMessage.text = requireActivity().getString(R.string.no_over_pridction_yet)
        questionAdapter.notifyDataSetChanged()
        overAdapter.update(overList,selectedOverPos)

    }

    private fun callOverResultAPI() {

        if (utilsManager.isNetworkConnected()) {
            binding.tvMessage.text = requireActivity().getString(R.string.loading)
            binding.tvMessage.isVisible = true
            val json = JsonObject()
            json.addProperty("match_id", match_id)
            json.addProperty("over_id", over_id)
            viewModal.getOverResultDetails(
                requireActivity(), preferenceManager.getAuthToken(), json
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                questionList.clear()
                if (res.status) {
                    overResultData = res.data
                    questionList.addAll(res.data.user_prediction)
                    setOverResultDataUI(res.message)
                } else {
                    showErrorToast(res.message)
                    binding.tvMessage.text = res.message
                    binding.tvMessage.isVisible = questionList.isEmpty()
                }



            })
        }else{
            binding.tvMessage.text = requireActivity().getString(R.string.no_internet_connection_please_try_again)
            binding.tvMessage.isVisible = true
        }


    }

    private fun setOverResultDataUI(message: String) {
        questionAdapter.notifyDataSetChanged()
        binding.tvMessage.isVisible = questionList.isEmpty()
        binding.tvMessage.text = message
            binding.clvResultBox.isVisible = overResultData.is_result
            binding.clvYourPrediction.isVisible = !overResultData.is_result
            if (overResultData.is_result) {
                binding.tvResultValue. isVisible =overResultData.is_result
                binding.imgGoliathBanner.isVisible = overResultData.correct_counts == questionList.size
                binding.tvResultValue.text = "${overResultData.correct_counts} / ${questionList.size} "
                binding.tvResultMessage.text = overResultData.message
                if (overResultData.correct_counts < 5) {
                    binding.tvTotalAmount.isVisible = false
                    binding.tvWon.isVisible = true
                    binding.tvWon.text = requireActivity().getString(R.string.sorry_you_lost_the)

                }else if (overResultData.correct_counts ==questionList.size) {
                    binding.tvResultMessage.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorText))
                    binding.tvTotalAmount.isVisible = true
                    binding.tvWon.isVisible = true
                    binding.tvWon.text = requireActivity().getString(R.string.you_won_the)
                    binding.tvTotalAmount.setText("${overResultData.winning_message}")
                } else {
                    binding.tvTotalAmount.isVisible = true
                    binding.tvWon.isVisible = false
                    binding.tvTotalAmount.setText("${overResultData.winning_message}")
                }

            }
            else if (overResultData.is_cancel) {
                binding.clvResultBox.isVisible = overResultData.is_cancel
                binding.clvYourPrediction.isVisible = !overResultData.is_cancel
                binding.imgGoliathBanner.isVisible = overResultData.is_result
                binding.tvResultValue.text = getString(R.string.prediction_cancelled)
                binding.tvWon.text =""
                binding.tvResultMessage.text = overResultData.message
                    binding.tvTotalAmount.isVisible = true
                    binding.tvTotalAmount.setText(overResultData.cancel_message)


            }else{
                binding.clvYourPrediction.isVisible = true

            }



    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}