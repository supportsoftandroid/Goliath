package com.fantasy.goliath.model

data class QuestionSaveRes(
    val `data`: QuestionSaveData,
    val message: String,
    val status: Boolean
)

data class QuestionSaveData(
    var is_wallet_recharge: Boolean=false,
    var currency_symbol: String="₹",
    var wallet_balance: String="0",
    var wallet_balance_show: String="0"

)



