package com.fantasy.goliath.model

data class OverResultDetailsRes(
    val `data`: OverResultData,
    val message: String,
    val status: Boolean
)

data class OverResultData(
    var correct_counts: Int=0,
    var winning_amount: String="0",
    var is_cancelled: Boolean=false,
    var is_result: Boolean,
    var result_message: String,
    var message: String,
    var winning_message: String,
    var cancel_message: String="",
    var user_prediction: ArrayList<QuestionAnsItem> =arrayListOf <QuestionAnsItem>(),


    )



