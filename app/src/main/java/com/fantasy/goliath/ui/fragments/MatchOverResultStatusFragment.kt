package com.fantasy.goliath.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.FragmentMatchPredictionStatusBinding
import com.fantasy.goliath.databinding.ListInningTabRowItemBinding
import com.fantasy.goliath.databinding.ListOverStatusItemBinding
import com.fantasy.goliath.model.InningItem

import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.model.OverItem
import com.fantasy.goliath.model.OverResultData
import com.fantasy.goliath.model.QuestionAnsItem
import com.fantasy.goliath.ui.adapter.InningItemAdapter
import com.fantasy.goliath.ui.adapter.InningTabAdapter
import com.fantasy.goliath.ui.adapter.MatchOverTabAdapter
import com.fantasy.goliath.ui.adapter.QuestionAnswerAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.getMatchStatus
import com.fantasy.goliath.utility.printLog

import com.fantasy.goliath.viewmodal.MatchOverResultViewModel
import com.google.gson.JsonObject
import com.health.kharma.ui.adapters.MyAdapter

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
    lateinit var loginResponse: LoginResponse

    lateinit var inningsAdapter: InningTabAdapter
    lateinit var overAdapter: MatchOverTabAdapter
    lateinit var questionAdapter: QuestionAnswerAdapter
    var inningsList = arrayListOf<InningItem>()
    var overList = arrayListOf<OverItem>()
    var questionList = arrayListOf<QuestionAnsItem>()

    lateinit var matchItem: MatchItem
    lateinit var overResultData: OverResultData
    lateinit var overItem: OverItem
    lateinit var match_id: String
    var over_id = ""

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
                OverWiseResultFragment.newInstance("add")
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
            inningsAdapter.notifyDataSetChanged()
            overList.clear()
            questionList.clear()
            binding.clvResultBox.isVisible = false
            binding.tvMessage.isVisible = true
            binding.tvMessage.text = requireActivity().getString(R.string.loading)
            questionAdapter.notifyDataSetChanged()
            if (inningsList[pos].overs.size > 0) {
                overList.addAll(inningsList[pos].overs)
                overItem = overList[0]
                over_id = overItem.over_id
                updateQuestionResultUI()
                callOverResultAPI()
            } else {

                updateQuestionResultUI()
            }


        })



        binding.rvInningsList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvInningsList.adapter = inningsAdapter


        overAdapter = MatchOverTabAdapter(
            requireActivity(),
            overList,
            { pos, type -> onOverAdapterClick(pos, type) })
        binding.rvOverList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvOverList.adapter = overAdapter
        questionList.clear()
        questionAdapter = QuestionAnswerAdapter(
            requireActivity(),
            questionList,
            { pos, type ->

            })
        binding.rvQuestionList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvQuestionList.adapter = questionAdapter


    }


    private fun setMatchDataUI() {
        binding.clvMatchCard.tvTournamentName.text = "${matchItem.competiton_name}"
        binding.clvMatchCard.tvMatchType.text = "${matchItem.formate}"
        binding.clvMatchCard.tvLeft.text = "${matchItem.teama.short_name}"
        binding.clvMatchCard.tvRight.text = "${matchItem.teamb.short_name}"
        binding.clvMatchCard.tvLeftFullName.text = "${matchItem.teama.name}"
        binding.clvMatchCard.tvRightFullName.text = "${matchItem.teamb.name}"
        binding.clvMatchCard.tvNote.isVisible = !matchItem.note.isEmpty()
        binding.clvMatchCard.tvNote.text = matchItem.note
        binding.clvMatchCard.tvDayTimeStatus.text = getMatchStatus(matchItem)


        if (matchItem.status.equals("live", true) || matchItem.status.equals("completed", true)) {
            binding.clvMatchCard.tvLeftScore.isVisible = true
            binding.clvMatchCard.tvRightScore.isVisible = true

            binding.clvMatchCard.tvLeftScore.text = "${matchItem.teama.scores_full}"
            binding.clvMatchCard.tvRightScore.text = "${matchItem.teamb.scores_full}"
            /*if (matchItem.status.equals("completed",true)){
                binding.clvMatchCard.tvLive.setBackgroundResource(R.drawable.button_bg_green)
            }else{
                binding.clvMatchCard.tvLive.setBackgroundResource(R.drawable.button_bg_red_round)
            }*/
        } else {

            binding.clvMatchCard.tvLeftScore.isVisible = false
            binding.clvMatchCard.tvRightScore.isVisible = false

        }
        loadImage(matchItem.teama.logo_url, binding.clvMatchCard.imgLeft)
        loadImage(matchItem.teamb.logo_url, binding.clvMatchCard.imgRight)
    }


    private fun onOverAdapterClick(pos: Int, type: String) {
        overItem = overList[pos]
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
                    matchItem = res.data.matchdetail
                    inningsList.addAll(res.data.matchdetail.innings)
                    overList.addAll(inningsList[0].overs)
                    if (overList.size > 0) {
                        overItem = overList[0]
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
        questionAdapter.notifyDataSetChanged()
        overAdapter.update(overList)
        binding.tvMessage.isVisible=overList.isEmpty()
        binding.tvMessage.text = requireActivity().getString(R.string.no_over_pridction_yet)


    }

    private fun callOverResultAPI() {

        if (utilsManager.isNetworkConnected()) {
            binding.tvMessage.text = requireActivity().getString(R.string.loading)
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

                } else {
                    showErrorToast(res.message)
                }

                setOverResultDataUI(res.message)

            })
        }


    }

    private fun setOverResultDataUI(message: String) {
        binding.tvMessage.text = message
        binding.tvMessage.isVisible = questionList.isEmpty()
        questionAdapter.notifyDataSetChanged()
        if (!questionList.isEmpty()) {
            binding.clvResultBox.isVisible = overResultData.is_result
            if (overResultData.is_result) {
                binding.imgGoliathBanner.isVisible =
                    overResultData.correct_counts == questionList.size
                binding.tvResultValue.text =
                    "${questionList.size} / ${overResultData.correct_counts} "
                binding.tvResultMessage.text = overResultData.message
                if (overResultData.correct_counts < 5) {
                    binding.tvTotalAmount.isVisible = false
                    binding.tvWon.text = requireActivity().getString(R.string.sorry_you_lost_the)
                } else {
                    binding.tvTotalAmount.isVisible = true
                    binding.tvWon.text = requireActivity().getString(R.string.you_won_the)
                    binding.tvTotalAmount.setText("₹ ${overResultData.winning_amount} will be transferred to your wallet.")
                }

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}