package com.fantasy.goliath.model

data class UserDetails(
    val id: String,
    var country_code: String,
    var email: String,
    var full_name: String,
    var guid: String="",
    var phone: String,
    var avatar_full_path: String,
    val status: String,
    val device_token: String,
    val wallet: String,
    var wallet_detail: WalletItem,

):SuperCastClass()