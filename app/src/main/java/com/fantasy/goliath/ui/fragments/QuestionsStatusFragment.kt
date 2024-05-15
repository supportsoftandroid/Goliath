package com.fantasy.goliath.ui.fragments

import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.utility.getMatchStatus
import com.fantasy.goliath.utility.isNetworkConnected
import com.fantasy.goliath.utility.setMatchCardUIData
import com.fantasy.goliath.utility.showPredictErrorDialog
import com.fantasy.goliath.utility.showPredictSuccessDialog
import com.fantasy.goliath.viewmodal.QuestionsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonArray
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
           // callQuestionAPI()
        }

        return binding.root
    }

    private fun clickListener() {

        binding.viewHeader.setClickListener(this)
        binding.btnSubmit.setOnClickListener() {

            var isfill=true
            val jsonArray=JsonArray()
            for (item in questionList){
                if (TextUtils.isEmpty(item.your_answer)){
                    isfill=false
                    showAlertMessageError("Please select answer of:- '${item.question}'")

                    break
                    return@setOnClickListener
                }else{
                    val json = JsonObject()
                    json.addProperty("question_id", item.question_id)
                    json.addProperty("answere", item.your_answer)
                    jsonArray.add(json)
                }
            }
            if (isfill){
                callSaveQuestionPredictionAPI(jsonArray)

            }


            /*showPredictErrorDialog(requireActivity(),
                { type, dialog -> onPredictCheck(type, dialog) })*/


        }


    }

    private fun onPredictCheck(type: String, dialog: BottomSheetDialog) {
        dialog.dismiss()


    }


    private fun initView() {

        binding.viewHeader.setTitle("${matchItem.short_title}  ")
        binding.tvOverName.text = "Over Number: ${over_name}"
        binding.tvPriceGuide.text = "You have to pay ₹${matchItem.pridiction_amount} from your wallet"
        loadImage(matchItem.teama.logo_url, binding.viewHeader.getToolBarView().imgTeam1)
        loadImage(matchItem.teamb.logo_url, binding.viewHeader.getToolBarView().imgTeam2)

        setMatchDataUI()
        binding.btnSubmit.text= getString(R.string.confirm_pay)


        questionList.addAll(matchItem.question)
        questionAdapter = QuestionAnswerStatusAdapter(
            requireActivity(),
            questionList,
            { pos, type ->   })
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        binding.rvList.adapter = questionAdapter

        setUIData("No questions available yet!")
    }

    private fun setMatchDataUI() {
        setMatchCardUIData(binding.clvMatchCard,matchItem)



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

                if (res.status) {
                   // matchItem = res.data.matchdetail


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

    private fun callSaveQuestionPredictionAPI(questionsArray: JsonArray) {

        if (utilsManager.isNetworkConnected()) {
            val json = JsonObject()
            json.addProperty("match_id", matchItem.match_id)
            json.addProperty("over_id", over_id)
            json.add("questionans", questionsArray)
            viewModal.saveQuestions(
                requireActivity(), preferenceManager.getAuthToken(), json
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                showErrorToast(res.message)

                if (res.status) {
                     showPredictSuccessDialog(requireActivity(),res.message,{ type, dialog ->
                         if (isNetworkConnected(requireActivity())) {
                             if (type.equals("new", true)) {
                                 val bundle=Bundle()
                                 bundle.putString("from", "new_prediction")
                                 bundle.putSerializable("match_item", matchItem)
                                 parentFragmentManager.setFragmentResult(
                                     Constants.ADD_OVER_REQUEST_KEY,
                                     bundle
                                 )
                                 dialog.dismiss()
                                 onBackPressed()
                                 onBackPressed()

                             } else {
                                 dialog.dismiss()
                                 movetToHome()


                             }
                         }

                     })


                } else {
                    showErrorToast(res.message)
                }


            })
        }


    }

    private fun movetToHome() {
          startActivity(Intent(requireActivity(), MainActivity::class.java).putExtra("from","my_prediction"))
          requireActivity().finishAffinity()
    }
}