package com.fantasy.goliath.model

data class AppContentRes(
    val `data`: AppContentData,
    val message: String,
    val status: Boolean
)

data class AppContentData(
    val id: String,
    val key: String,
    val name: String,
    val value: String,


)
