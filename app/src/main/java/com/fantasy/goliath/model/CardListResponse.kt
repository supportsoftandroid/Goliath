package com.fantasy.goliath.model

data class CardListResponse(
    val status: Boolean,
    val message: String,
    val strip_key: String,
    val strip_secrete: String,
    val data: ArrayList<CardData>,

    ) : SuperCastClass()

