package com.fantasy.goliath.model
    data class LoginResponse(
    val status: Boolean,
    val message: String,
    var data: LoginData,
) : SuperCastClass()
