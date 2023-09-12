package com.fantasy.goliath.model

data class CardData(
    val brand: String,
    val name: String,
    val exp_month: Int,
    val exp_year: Int,
    val id: String,
    val status: String,
    val last4: Int,
    var default: String="0",
    var isSelected: Boolean = false
)