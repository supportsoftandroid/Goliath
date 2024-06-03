package com.fantasy.goliath.model


import java.io.Serializable

data class WalletItem(
    val total_diposite: String,
    val total_winning: String,
    val total_fee_paid: String,
    val total_withdrawal: String,

) : SuperCastClass(), Serializable

