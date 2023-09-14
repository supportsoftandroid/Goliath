package com.fantasy.goliath.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.databinding.ActivityGuideGameBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.ui.adapter.GameGuideAdapter
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.StaticData

class GameGuideActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivityGuideGameBinding
    lateinit var adapter: GameGuideAdapter
    var dataList = mutableListOf<CommonDataItem>()
    val colorCode="#438DCA"
    override fun onCreate(savedInstanceState: Bundle?) {
        StaticData.adjustFontScale(this, resources.configuration, 1.0f)
        binding = ActivityGuideGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)
        binding.let {
            initView()
            clickListener()
        }

    }

    private fun clickListener() {
        binding.imgBack.setOnClickListener() {
            finish()
        }
        binding.btnNext.setOnClickListener() {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initView() {
        dataList.clear()

        dataList.add(CommonDataItem("Signup www.goliath101.com","",false))
        dataList.add(CommonDataItem("Make your match Selection","",false))
        var strText="Select The "+getBoldText("OVER(S)")+"  to play"
        dataList.add(CommonDataItem(strText,"",false))
        dataList.add(CommonDataItem("Select one or multiple OVERS","",false))
        dataList.add(CommonDataItem("Make your predictions","",false))
        dataList.add(CommonDataItem("Load your Wallet","",false))

          strText="Entry fee"+getBoldText("₹501")+"  per OVER"

        dataList.add(CommonDataItem(strText,"",false))
        dataList.add(CommonDataItem("Check the APP","",false))
        strText="See instant results"+getBoldText("WIN/LOSE")

        dataList.add(CommonDataItem(strText,"",false))
        dataList.add(CommonDataItem("Check your winning status.","",false))
        dataList.add(CommonDataItem("Check your wallet","",false))
        strText="Predictions for next "+getBoldText("OVER")
        dataList.add(CommonDataItem(strText,"",false))

        adapter = GameGuideAdapter(this, dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter
    }

    private fun getBoldText(text: String):String {
       return "<font color=#438DCA><b>$text</b></font>"

    }  private fun onAdapterClick(pos: Int, type: String) {

    }
}
