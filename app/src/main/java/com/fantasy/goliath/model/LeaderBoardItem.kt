package com.fantasy.goliath.model




data class LeaderBoardItem(
    val TotalGamesWinning: String,
    val TotalGamesPlayed: String,
    val user: UserItem,
    val user_id: String
)

data class UserItem(
    val avatar: String,
    val avatar_full_path: String,
    val country_code: String,
    val created_at: String,
    val deleted_at: String,
    val device_token: String,
    val device_type: String,
    val email: String,
    val email_verified_at: String,
    val first_name: String,
    val full_name: String,
    val id: String,
    val last_name: String,
    val otp: String,
    val otp_expired: String,
    val phone: String,
    val phone_verified_at: String,
    val role: String,
    val slug: String,
    val status: String,
    val timezone: String,
    val type: String,
    val updated_at: String,
    val wallet: String
):SuperCastClass()