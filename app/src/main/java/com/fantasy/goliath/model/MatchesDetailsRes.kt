package com.fantasy.goliath.model

data class MatchesDetailsRes(
    val `data`: MatchDetailsData,
    val message: String,
    val status: Boolean
)

data class MatchDetailsData(
    var matchdetail: MatchItem,

)



