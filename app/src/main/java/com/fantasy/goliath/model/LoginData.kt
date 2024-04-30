package com.fantasy.goliath.model


data class LoginData(
    val access_token: String,
    val token_type: String,
    var user: UserDetails,
) : SuperCastClass()
