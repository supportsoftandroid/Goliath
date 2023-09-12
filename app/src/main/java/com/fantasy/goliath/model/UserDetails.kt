package com.fantasy.goliath.model

data class UserDetails(
    val area: String,
    val avatar: String,
    val bio: String,
    val city: String,
    val country: String,
    val country_code: String,
    var email: String,
    var email_verified_at: String,
    val first_name: String,
    val full_address: String,
    val full_name: String,
    val id: String,
    val last_name: String,
    val stripe_id: String,
    val latitude: String,
    val longitude: String,
    val mobile: String,
    val state: String,
    var is_payment: String="0",
    val status: String,
    val zipcode: String
)