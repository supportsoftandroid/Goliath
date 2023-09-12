package com.fantasy.goliath.model


data class LoginResponse(
    val status: Boolean,
    val message: String,
    val access_token: String,
    val token_type: String,
    var user: UserDetails,
) : SuperCastClass()
