package com.fantasy.goliath.model

data class CommonResponse(
    val message: String,
    val status: Boolean
) : SuperCastClass()