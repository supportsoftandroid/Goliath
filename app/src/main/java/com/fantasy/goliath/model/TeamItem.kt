package com.fantasy.goliath.model

import java.io.Serializable
data class TeamItem(
    val logo_url: String,
    val name: String,
    val overs: String,
    val scores: String,
    val scores_full: String,
    val short_name: String,
    val team_id: String,
    val thumb_url: String
):SuperCastClass(),Serializable