package com.fantasy.goliath.model

import java.io.Serializable
data class OverItem(
    val over_id: String,
    val over_number: String,
    val over_status: String,
    var is_selected: Boolean=false,
):SuperCastClass(),Serializable