package com.fantasy.goliath.model

data class MatchesResponse(
    val `data`: MatchData,
    val message: String,
    val status: Boolean
)

data class MatchData(
    var matchlist: MatchList,

)
data class MatchList(

    val current_page: Int,
    var data: ArrayList<MatchItem>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val next_page_url: String,
    val path: String,
    val per_page: Int,
    val prev_page_url: String,
    val to: Int,
    val total: Int
)



