package com.fantasy.goliath.model

import java.io.Serializable

data class QuestionListRes(
    val `data`: MatchItem,
    val message: String,
    val status: Boolean
):SuperCastClass(),Serializable





