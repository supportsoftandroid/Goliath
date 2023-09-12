package com.fantasy.goliath.model

data class CardResponse(
    val status: Boolean,
    val message: String,
    val data: CardData,

    ) : SuperCastClass()

