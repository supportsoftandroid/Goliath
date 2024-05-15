package com.fantasy.goliath.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentAddOverBinding
import com.fantasy.goliath.databinding.ListOverStatusItemBinding


import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.InningItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.model.MatchItem
import com.fantasy.goliath.model.OverItem
import com.fantasy.goliath.ui.adapter.InningItemAdapter
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.Constants
import com.fantasy.goliath.utility.getMatchStatus
import com.fantasy.goliath.utility.printLog
import com.fantasy.goliath.utility.setMatchCardUIData


import com.fantasy.goliath.viewmodal.AddOverViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.health.kharma.ui.adapters.MyAdapter

class AddOverFragment : BaseFragment() {
    companion object {
        fun newInstance(from: String, matchItem: MatchItem): AddOverFragment {
            val args = Bundle()
            args.putString("from", from)
            args.putSerializable("match_item", matchItem)
            val fragment = AddOverFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModal by lazy { ViewModelProvider(this)[AddOverViewModel::class.java] }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentAddOverBinding.inflate(layoutInflater)
    }
    lateinit var loginResponse: LoginResponse
    lateinit var adapter: InningItemAdapter
    var inningsList = arrayListOf<InningItem>()
    var overStatusList = arrayListOf<CommonDataItem>()
    lateinit var matchItem: MatchItem
    lateinit var overItem: OverItem
    lateinit var match_id: String
    var over_id = ""
    var over_name= ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val root: View = binding.root
        binding.let {
            binding.btnConform.isVisible = false
            overStatusList.clear()
            matchItem = arguments?.getSerializable("match_item") as MatchItem
            match_id = matchItem.match_id
            inningsList.clear()
            overStatusList.clear()

            initView()
            clickListener()
            callMatchDetailsAPI()
        }

        return root
    }

    private fun clickListener() {

        binding.clvHeader.setClickListener(this)
        binding.btnConform.setOnClickListener() {
            if (!over_id.isEmpty()){
                matchItem.match_id=match_id


                replaceFragmentAddBackStack(
                    AddQuestionsFragment.newInstance("add",over_id,over_name,matchItem)
                )
            }
           /* showWalletErrorDialog(requireActivity(),
                { type, dialog -> onWalletCheck(type, dialog) })*/

        }
        binding.tvResults.setOnClickListener() {
            replaceFragmentAddBackStack( MatchOverResultStatusFragment.newInstance("over",matchItem))


        }

    }

    private fun onWalletCheck(type: String, dialog: BottomSheetDialog) {
        dialog.dismiss()

    }


    private fun initView() {
        inningsList.clear()
       setMatchDataUI()
        adapter = InningItemAdapter(requireActivity(), inningsList, { parentPosition, pos, type ->
            overItem = inningsList[parentPosition].overs[pos]
            over_id = overItem.over_id
            over_name = overItem.over_number
            binding.btnConform.isVisible = true

        })

        binding.rvList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        binding.rvList.adapter = adapter
        binding.rvList.setNestedScrollingEnabled(false)
        overStatusList.clear()

        overStatusList.add(CommonDataItem("Overs Completed", "completed", false))
        overStatusList.add(CommonDataItem("Ongoing Over", "ongoing", false))
        overStatusList.add(CommonDataItem("Predicted Overs", "predicted", false))
        overStatusList.add(CommonDataItem("Available Overs", "available", false))
        overStatusList.add(CommonDataItem("Selected Over", "selected", false))
        overStatusList.add(CommonDataItem("Upcoming Overs", "upcoming", false))

        val adapterStatus = MyAdapter(
            R.layout.list_over_status_item,
            overStatusList
        ) { view, data, pos ->

            ListOverStatusItemBinding.bind(view).apply {
                tvTitle.text = data.title
                when (data.type.lowercase()) {
                    "completed" -> {

                        // tvTitle.setTextColor(ContextCompat.getColor(rea, R.color.colorRed))
                        imgProduct.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.colorRedLight
                            )
                        )
                    }

                    "predicted" -> {

                        // tvTitle.setTextColor(ContextCompat.getColor(rea, R.color.colorRed))
                        imgProduct.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.color2EB100
                            )
                        )
                    }

                    "ongoing" -> {

                        // tvTitle.setTextColor(ContextCompat.getColor(rea, R.color.colorRed))
                        imgProduct.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.colorRed
                            )
                        )
                    }

                    "selected" -> {
                        imgProduct.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.app_color
                            )
                        )
                    }

                    "available" -> {
                        imgProduct.setBackgroundResource(R.drawable.border_layout_blue_radius_5)
                    }

                    "upcoming" -> {
                        imgProduct.setBackgroundResource(R.drawable.border_layout_blue_light_radius_5)
                    }


                }


            }
        }

        binding.rvOverStatusList.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.rvOverStatusList.adapter = adapterStatus
        binding.rvOverStatusList.suppressLayout(false)
        setFragmentResultListener(Constants.ADD_OVER_REQUEST_KEY) { key, bundle ->
            // Any type can be passed via to the bundle
            val from = bundle.getString("from").toString()
            if (from.equals("new_prediction")) {
                over_id=""
                binding.btnConform.isVisible=false
                val item=bundle.getSerializable("match_item") as MatchItem
                matchItem.match_id=item.match_id

                callMatchDetailsAPI()
            }
            // Do something with the result...
        }
    }

    private fun setMatchDataUI() {
        loadImage(matchItem.teama.logo_url, binding.clvHeader.getToolBarView().imgTeam1)
        loadImage(matchItem.teamb.logo_url, binding.clvHeader.getToolBarView().imgTeam2)
        binding.clvHeader.setTitle("${matchItem.short_title}  ")
        setMatchCardUIData(binding.clvMatchCard,matchItem)

    }

    private fun callMatchDetailsAPI() {

        if (utilsManager.isNetworkConnected()) {

            val json = JsonObject()
            json.addProperty("match_id", match_id)
            viewModal.getMatchesDetails(
                requireActivity(), preferenceManager.getAuthToken(), json
            ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { res ->
                inningsList.clear()
                if (res.status) {
                    matchItem = res.data.matchdetail
                    inningsList.addAll(res.data.matchdetail.innings)
                    setMatchDataUI()

                } else {
                    showErrorToast(res.message)
                }
                printLog("inningsList",inningsList.size.toString())
                setUIData(res.message)

            })
        }


    }

    private fun setUIData(message: String) {
        adapter.notifyDataSetChanged()

        if (inningsList.isEmpty()) {
            binding.tvMessage.isVisible = true
            binding.rvOverStatusList.isVisible = false
            binding.tvMessage.text = message
        } else {
            binding.tvMessage.isVisible = false
            binding.rvOverStatusList.isVisible = true
        }
        binding.clvHeader.setTitle(matchItem.short_title)
        loadImage(matchItem.teama.logo_url, binding.clvHeader.getToolBarView().imgTeam1)
        loadImage(matchItem.teamb.logo_url, binding.clvHeader.getToolBarView().imgTeam2)

    }


    override fun onDestroyView() {
        super.onDestroyView()

    }
}