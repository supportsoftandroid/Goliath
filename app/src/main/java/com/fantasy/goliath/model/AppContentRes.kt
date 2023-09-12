package com.fantasy.goliath.model

data class AppContentRes(
    val `data`: AppContentData,
    val message: String,
    val status: Boolean
)

data class AppContentData(
    val about_app: AboutApp,
    val faqs: Faqs,
    val privacy_and_policy: PrivacyAndPolicy,
    val support_and_help: SupportAndHelp,
    val terms_and_conditions: TermsAndConditions
)

data class AboutApp(
    val description: String,
    val id: Int,
    val page: String,
    val slug: String
)

data class Faqs(
    val description: String,
    val id: Int,
    val page: String,
    val slug: String
)

data class PrivacyAndPolicy(
    val description: String,
    val id: Int,
    val page: String,
    val slug: String
)

data class SupportAndHelp(
    val created_at: String,
    val description: String,
    val id: Int,
    val page: String,
    val slug: String,
    val status: String,
    val updated_at: String
)

data class TermsAndConditions(
    val description: String,
    val id: Int,
    val page: String,
    val slug: String
)