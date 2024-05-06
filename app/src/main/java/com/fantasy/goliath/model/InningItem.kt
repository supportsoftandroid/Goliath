package com.fantasy.goliath.model

import java.io.Serializable
data class InningItem(
    val inning_name: String,
    val inning_status: String,
    var overs: ArrayList<OverItem>,


):SuperCastClass(),Serializable