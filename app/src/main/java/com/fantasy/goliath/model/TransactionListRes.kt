package com.fantasy.goliath.model

data class TransactionListRes(
    val `data`: TransactionData,
    val message: String,
    val status: Boolean
)

data class TransactionData(
    var transaction: Transactions,

)
data class Transactions(
    val current_page: Int,
    var data: ArrayList<TransactionsItem>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val next_page_url: String,
    val path: String,
    val per_page: Int,
    val prev_page_url: String,
    val to: Int,
    val total: Int

)




