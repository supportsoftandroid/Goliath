package com.fantasy.goliath.model

data class TransactionsItem(
    val amount: String,
    val amount_show: String,
    val created_at: String,
    val deleted_at: String,
    val id: String,
    val note: String,
    val payment_id: String,
    val payment_mode: String,
    val reference_id: String,
    val transaction_type: String,
    val updated_at: String,
    val user_id: String
):SuperCastClass()