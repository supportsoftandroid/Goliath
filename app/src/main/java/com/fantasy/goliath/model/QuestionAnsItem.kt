package com.fantasy.goliath.model


import java.io.Serializable

data class QuestionAnsItem(
    val id: String,
    val question_id: String,
    val overs: String,
    val question: String,
    var your_answer: String="",
    val your_result: String="",



    ) : SuperCastClass() ,Serializable