package com.fantasy.goliath.model

data class LeaderBoardListRes(
    val `data`: LeaderBoardData,
    val message: String,
    val status: Boolean
)

data class LeaderBoardData(
    var leaderboard: ArrayList<LeaderBoardItem>,

)




