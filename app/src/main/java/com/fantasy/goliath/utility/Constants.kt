package com.fantasy.goliath.utility

object Constants {
    const val COUNTRY_NAME = "AU"
    const val COUNTRY_CODE = "+61"
    const val SHARED_PREFERENCE_FILE_NAME = "wawfitness"
    const val DEVICE_TYPE = "android"
    const val YOUTUBE_VIDEO = "urlb"
    const val STRIPE_PUBLISH_KEY =
        "pk_test_51LicFNFkV20vz5IvboMHk365cdTOHJ310eS4NgBJ2avgBTWLRgnMrQZVEEOTr9Y3s6YB7F6gtFkAhXSlx27rKd1S00e7dSexd3"


    /*  API CALL CONSTANTS  */

    //const val ACCEPT_JSON_HEADER = "Content-Type: application/json,Accept: application/json"

    /* val  JSON_HEADER = arrayOf(
         "Content-Type: application/json",
         "Accept: application/json",


     )*/


   // const val ACCEPT_JSON_HEADER = "Content-Type: application/json"
    const val ACCEPT_JSON_HEADER = "Accept: application/json"
    const val PROFILE_EDIT_REQUEST_KEY = "profile_edit_"

    /*  APP URLs    */
    const val LIVE_URL = "https://projects.commnext.com.au/goliath/public/api/"
    const val API_WELCOME_SCREEN = "on-boarding"

    const val API_SEND_OTP = "auth/send-otp"
    const val API_VERIFY_OTP = "auth/verify-otp"
    const val API_HOW_TO_PLAY = "how-to-play"

    const val API_CHECK_EMAIL = "check-email"
    const val API_UPDATE_EMAIL = "update-email"
    const val API_COMPETITION_LIST = "competition-list"
    const val API_MATCHES_LIST = "matches-list"
    const val API_MATCH_DETAIL = "matches-detail"
    const val API_QUESTION_LIST_FOR_DETAIL = "question-list-for-over"
    const val API_SAVE_USER_PREDICTION = "save-user-prediction"

    const val API_CATEGORY_DETAILS = "category-detail"
    const val API_ITEM_DETAILS = "item-detail"
    const val API_PROFILE_DETAILS = "user"
    const val API_EDIT_PROFILE = "update-profile"
    const val API_CHANGE_PASSWORD = "change-password"

    const val API_ADD_CARD = "add-payment-method"
    const val API_GET_CARD_LIST = "payment-methods"
    const val API_DELETE_CARD = "delete-payment-method"
    const val API_DEFAULT_CARD = "add-default-payment-method"

    const val API_LOGOUT = "logout"
    const val API_DELETE_ACCOUNT = "deleteAccount"
    const val API_APP_CONTENT_PAGES = "pages"
    const val API_HELP = "help"

    /* IMPORTANT TEXTUAL CONSTANTS CONSTANTS */
    const val SOMETHING_WENT_WRONG_ERROR = "Oops! Something went wrong"

    /*  ERROR AND ALERT MESSAGES    */
    const val ERROR_NO_INTERNET_ALERT = "No Internet Connection"
    const val ERROR_ALERT = "Error"

    const val ERROR_MESSAGE = "Please Check your Internet Connection and Try Again."
    const val ERROR_ALERT_INVALID_TOKEN = "Invalid Token"
    const val ERROR_MESSAGE_INVALID_TOKEN = "Please Login and Try Again"
    const val ERROR_INVALID_TOKEN = "Invalid Token"

    /***** PERMISSION ACCESS CONSTANTS *****/
    /*  SHARED PREFERENCES CONSTANTS  */

    const val KEY_CHECK_LOGIN = "check_login"
    const val KEY_ACCESS_TOKEN = "token"
    const val KEY_USER_NAME = "user_name"
    const val KEY_LOGIN_DATA = "login_data"
    const val IN_APP_PERMISSION = "Need Permissions"
    const val PERMISSIONS_ACCESS_MESSAGE =
        "This app requires permissions to access your location and storage"
 const val EDIT_PROFILE_REQUEST_KEY = "edit_Profile"
 const val EDIT_PROFILE_OTHER_REQUEST_KEY = "edit_Profile_other"
}