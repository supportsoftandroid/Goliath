package com.fantasy.goliath.network


import com.fantasy.goliath.model.*
import com.fantasy.goliath.utility.Constants
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

public interface ApiInterface {

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_SEND_OTP)
    fun loginSignupSendOTP(
        @Body body: JsonObject
    ): Call<LoginResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_VERIFY_OTP)
    fun verify_otp(
        @Body body: JsonObject
    ): Call<LoginResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_HOW_TO_PLAY)
    fun how_to_play(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<HowToPlayResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_CHECK_EMAIL)
    fun check_email(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_UPDATE_EMAIL)
    fun update_email(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<CommonResponse>


    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_COMPETITION_LIST)
    fun getCompetitionList(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_MATCHES_LIST)
    fun getMatchesList(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Body body: JsonObject
    ): Call<MatchesResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_MATCH_DETAIL)
    fun getMatchDetails(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<MatchesDetailsRes>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_QUESTION_LIST_FOR_DETAIL)
    fun getQuestionList(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<MatchesDetailsRes>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_SAVE_USER_PREDICTION)
    fun saveUserPrediction(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<MatchesDetailsRes>


    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_MY_PREDICTIONS)
    fun getMyPredictionList(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int,
        @Body body: JsonObject
    ): Call<MatchesResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_MATCH_PREDICTED_OVER_DETAIL)
    fun getMatchPredictedOverDetails(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<MatchesDetailsRes>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_MATCH_PREDICTED_OVER_RESULT)
    fun getMatchPredictedOverResult(
        @Header("Authorization") authorization: String,
        @Body body: JsonObject
    ): Call<OverResultDetailsRes>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @GET(Constants.API_PROFILE_DETAILS)
    fun getProfile(
        @Header("Authorization") authorization: String?
    ): Call<LoginResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_EDIT_PROFILE)
    fun updateProfile(
        @Header("Authorization") authorization: String?,
        @Body json: JsonObject,
    ): Call<LoginResponse>

    @Multipart()
    @POST(Constants.API_EDIT_PROFILE)
    fun updateProfileImage(
        @Header("Authorization") authorization: String?,
        @Part file_1: MultipartBody.Part?,
    ): Call<LoginResponse>


    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_ADD_CARD)
    fun addCard(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CardResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_DELETE_CARD)
    fun deleteCard(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CardResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_DEFAULT_CARD)
    fun defaultCard(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CardResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @GET(Constants.API_GET_CARD_LIST)
    fun getCardList(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CardListResponse>


    /*  @POST(Constants.API_NOTIFICATION_LIST)
      fun getNotifications(
          @Header("Authorization") authorization: String?
      ): Call<NotificationResponse>

      @POST(Constants.API_NOTIFICATION_READ)
      fun readNotifications(
          @Header("Authorization") authorization: String?,
          @Body body: JsonObject
      ): Call<NotificationResponse>

      @POST(Constants.API_NOTIFICATION_DELETE)
      fun deleteNotifications(
          @Header("Authorization") authorization: String?,
          @Body body: JsonObject
      ): Call<NotificationResponse>
  */

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_CHANGE_PASSWORD)
    fun change_password(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @GET(Constants.API_APP_CONTENT_PAGES)
    fun getAppContentPages(


    ): Call<AppContentRes>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_HELP)
    fun helpCenter(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)

    @POST(Constants.API_LOGOUT)
    fun logout(
        @Header("Authorization") authorization: String?,
    ): Call<CommonResponse>

    @DELETE(Constants.API_DELETE_ACCOUNT)
    fun deleteAccount(
        @Header("Authorization") authorization: String?,
    ): Call<CommonResponse>

}