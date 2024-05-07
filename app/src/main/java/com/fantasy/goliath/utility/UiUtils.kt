package com.fantasy.goliath.utility


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.*
import android.location.Address
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.provider.MediaStore
import android.provider.Settings
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.fantasy.goliath.BuildConfig
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.DialogBottomAddAmountBinding
import com.fantasy.goliath.databinding.DialogBottomAddCardBinding
import com.fantasy.goliath.databinding.DialogImageUploadBinding
import com.fantasy.goliath.databinding.DialogPredictErrorBinding
import com.fantasy.goliath.databinding.DialogUpdateProfileBinding
import com.fantasy.goliath.databinding.DialogVerifyOtpBinding
import com.fantasy.goliath.databinding.DialogWalletBalanceErrorBinding
import com.fantasy.goliath.ui.activities.AuthActivity
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.utility.Constants.ERROR_ALERT
import com.fantasy.goliath.utility.Constants.ERROR_MESSAGE_INVALID_TOKEN
import com.fantasy.goliath.utility.Constants.ERROR_NO_INTERNET_ALERT
import com.fantasy.goliath.utility.Constants.KEY_CHECK_LOGIN
import com.github.dhaval2404.imagepicker.ImagePicker
import com.goodiebag.pinview.Pinview
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.stripe.android.view.CardMultilineWidget
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


const val MILLIS_PER_DAY = 24 * 60 * 60 * 1000L
val msgDateFormat = "dd-MMM-yyyy HH:mm:ss a"
const val FCM_BROADCAST: String = "FCM_BROADCAST"
const val KEY_IS_CHAT = "is_chat"
const val CHANNEL_ID = "goliath"
const val CHANNEL_NAME = "goliath Channel"
const val CHANNEL_DESCRIPTION = "goliath Channel Description"
const val PERMISSION_REQUEST_CODE = 133
const val CAMERA_REQUEST_CODE = 201
const val GALLARY_REQUEST_CODE = 202
const val IMAGE_CROP_REQUEST_CODE = 277
const val AUTOCOMPLETE_REQUEST_CODE = 653
const val LOCATION_MAP_ADD_REQUEST_CODE = 9883
const val MANAGE_ALL_FILES_ACCESS_REQUEST_CODE: Int = 2296
const val REQUEST_CODE_ASK_CAMERA_STORAGE_PERMISSIONS = 1324
const val PERMISSION_ALL = 2296

val PERMISSIONSList = getPermission()
val PERMISSIONS_CAMERA_STORAGE_LIST = getCameraStoragePermission()
  const val expression =
    "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
const val EMAIL_PATTERN = ("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
/*  val  regex =("^[+]{1}(?:[0-9\\-\\(\\)\\/"
  "\\.]\\s?){6, 15}[0-9]{1}$");*/
fun getTimeZoneId(): String {
    val tz = TimeZone.getDefault()
    return tz.id

}

fun getVideoId(videoUrl: String): String {
    if (TextUtils.isEmpty(videoUrl) || videoUrl.trim { it <= ' ' }.length <= 0) {
        return ""
    }
    val pattern = Pattern.compile(expression)
    val matcher: Matcher = pattern.matcher(videoUrl)
    try {
        if (matcher.find()) return matcher.group()
    } catch (ex: ArrayIndexOutOfBoundsException) {
        ex.printStackTrace()
    }
    return ""
}

fun getPermission(): Array<String> {
    var permissionList = arrayListOf<String>()
    permissionList.add(Manifest.permission.INTERNET)
    permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE)
    permissionList.add(Manifest.permission.CAMERA)
    permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
    permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
    if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)
    } else if (SDK_INT >= Build.VERSION_CODES.R) {
        permissionList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
    } else {
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    return permissionList.toTypedArray()
}
fun isValidEmailId(target: String?): Boolean {
    return target != null && Pattern.compile(EMAIL_PATTERN).matcher(
        target
    ).matches()
}
fun getNumberFromString(text: String): String {
    // var string = "vsdhfnmsdbvfuf121535435aewr"
      if (isValidEmailId(text)){
        return ""
      }else{
        printLog("result", text.filter { it.isDigit() })
       return text.filter { it.isDigit() }
    }


}

fun getCameraStoragePermission(): Array<String> {
    val permissionList = arrayListOf<String>()

    permissionList.add(Manifest.permission.CAMERA)
    permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissionList.add(Manifest.permission.READ_MEDIA_IMAGES)

    }
    /* else if (SDK_INT >= Build.VERSION_CODES.R) {
         permissionList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
     } else {
         permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
     }*/
    // val array: Array<String> = permissionList.toTypedArray()
    return permissionList.toTypedArray()
}


fun checkStorageAndCameraPermission(mContext: Context?): Boolean {
    val isGranted = hasPermissions(mContext, PERMISSIONS_CAMERA_STORAGE_LIST.toString())
    if (!isGranted) {
        requestStorageAndCameraPermission(mContext)
    }
    return isGranted
}

fun checkFileAccessPermission(context: Context): Boolean {
    val isGranted = checkStorageAndCameraPermission(context)
    checkStorageAndCameraPermission(context)
    /* if (SDK_INT >= Build.VERSION_CODES.R) {
         isGranted = Environment.isExternalStorageManager()
         if (isGranted) {
             checkStorageAndCameraPermission(context)
         }
     } else {
         isGranted = checkStorageAndCameraPermission(context)
     }*/
    return isGranted
}
/*
        fun getFlexBoxLayoutManager(mContext: Context?): FlexboxLayoutManager {
            val layoutManager = FlexboxLayoutManager(mContext)
            layoutManager.setFlexWrap(WRAP)
            layoutManager.setFlexDirection(FlexDirection.ROW)
            layoutManager.setJustifyContent(JustifyContent.FLEX_START)
            layoutManager.setAlignItems(AlignItems.FLEX_START)
            return layoutManager
        }*/

fun changeStatusBarColor(context: Context, from: String) {
    // Set the status bar color
    val window = (context as Activity).window
    var colorValue = ContextCompat.getColor(context, R.color.colorAppBg)
    when (from) {
        "home" -> {
            colorValue = ContextCompat.getColor(context, R.color.app_color)
        }

        else -> {
            colorValue = ContextCompat.getColor(context, R.color.colorAppBg)
        }
    }
    //  window.decorView(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    // window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    //   window.statusBarColor = colorValue


}


fun requestFileAccessPermission(activity: Activity) {
    if (!checkFileAccessPermission(activity)) {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data =
                    Uri.parse(String.format("package:%s", activity.getPackageName()))
                activity.startActivityForResult(
                    intent,
                    MANAGE_ALL_FILES_ACCESS_REQUEST_CODE
                )

            } catch (e: java.lang.Exception) {
                requestStorageAndCameraPermission(activity)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                activity.startActivityForResult(
                    intent, MANAGE_ALL_FILES_ACCESS_REQUEST_CODE
                )
            }
        } else {
            //below android 11
            requestStorageAndCameraPermission(activity)
        }
    } else {
        requestStorageAndCameraPermission(activity)
    }
}

fun requestStorageAndCameraPermission(mContext: Context?) {
    ActivityCompat.requestPermissions(
        (mContext as Activity?)!!, PERMISSIONS_CAMERA_STORAGE_LIST,
        REQUEST_CODE_ASK_CAMERA_STORAGE_PERMISSIONS
    )
}

fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
    if (SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission!!
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
    }
    return true
}

fun getMatchTime(selectedDate: String): String {
    return selectedDate.toDate().formatTo("HH:mm")

}

fun getMatchDate(selectedDate: String): String {
    return selectedDate.toDate().formatTo("dd-MMM-yyyy")

}

fun Date.formatTo(dateFormat: String): String {
    val timeZone: TimeZone = TimeZone.getDefault()
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun String.toDate(): Date {
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    parser.timeZone = TimeZone.getTimeZone("UTC")
    return parser.parse(this)
}
fun changeLastChatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    val output = SimpleDateFormat("HH:mm a")
    var d: Date? = inputFormat.parse(inputDate)
    val formatted: String = output.format(d)
    /*  Log.i("DATE", "" + formatted)*/
    return formatted

}


fun getURLForResource(resourceId: Int): String {
    //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
    return Uri.parse("android.resource://" + R::class.java.getPackage().name + "/" + resourceId)
        .toString()
}

fun takeNewPicture(activity: Activity): Uri {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val values = ContentValues(3)
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    val cameraImagePath = activity.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
    )!!
    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImagePath)
    activity.startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
    return cameraImagePath
}


fun pickImageFromGallary(activity: Activity) {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    activity.startActivityForResult(intent, GALLARY_REQUEST_CODE)

}

fun dateFormat(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    val output = SimpleDateFormat("MMM dd,yyyy")
    val d: Date? = inputFormat.parse(inputDate)
    val formatted: String = output.format(d)
    //  Log.i("DATE", "" + formatted)
    return formatted

}

fun createFileMultiPart(
    file: File,
    fileName: String,
    fileType: String
): MultipartBody.Part {
    val reqFile: RequestBody = file.asRequestBody(fileType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(
        fileName, file.getName(), reqFile
    )
}

fun createRequestBody(parameter: String): RequestBody {
    return parameter.toRequestBody("text/plain".toMediaTypeOrNull())
}

// defining our own password pattern
fun getAndroidDeviceId(context: Context): String {
    val android_id =
        Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
    return android_id
}// defining our own password pattern


private val PASSWORD_PATTERN = Pattern.compile(
    "^" +
            "(?=.*[@#$%^&+=])" +  // at least 1 special character
            "(?=\\S+$)" +  // no white spaces
            "(?=\\S+$)" +  // no white spaces
            ".{8,}" +  // at least 8 characters
            "$"
)

fun printLog(str: String, message: String) {
    if (BuildConfig.DEBUG) {
        Log.e(str + "=====>", message)
    }
}
fun showSnackBar(activity: Activity, msg: String) {
    val snackbar = Snackbar.make(
        activity.findViewById(if (activity is MainActivity) R.id.container_main else android.R.id.content),
        msg,
        Snackbar.LENGTH_SHORT
    )

    val snackbarView = snackbar.view

    val layoutParams =
        if (activity is MainActivity) snackbarView.layoutParams as CoordinatorLayout.LayoutParams else snackbarView.layoutParams as FrameLayout.LayoutParams
    layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    if (layoutParams is FrameLayout.LayoutParams) {
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
    }
    if (layoutParams is CoordinatorLayout.LayoutParams) {
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
    }
    snackbarView.layoutParams = layoutParams

    val snackTextView =
        snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
    snackTextView.maxLines = 10
    snackbar.show()
}
fun setMatchTeamViewColor(
    context: Context,
    teamName: String,
    imgTeam: ImageView,
    teamBorder: View
) {

    var teamColor = R.color.colorTeamGT
    when (teamName) {
        "GT" -> {
            teamColor = R.color.colorTeamGT
        }

        "PBKS" -> {
            teamColor = R.color.colorTeamPBKS
        }

        "CSK" -> {
            teamColor = R.color.colorTeamCSK
        }

        "KKR" -> {
            teamColor = R.color.colorTeamKKR
        }

        "DC" -> {
            teamColor = R.color.colorTeamDC
        }

        "LSG" -> {
            teamColor = R.color.colorTeamLSG
        }

        "SRH" -> {
            teamColor = R.color.colorTeamSRH
        }

        "RR" -> {
            teamColor = R.color.colorTeamRR
        }

        "MI" -> {
            teamColor = R.color.colorTeamMI
        }

        "RCB" -> {
            teamColor = R.color.colorTeamRCB
        }
    }





    teamBorder.setBackgroundColor(context.resources.getColor(teamColor))
    // imgTeam.setColorFilter(ContextCompat.getColor(context, teamColor), PorterDuff.Mode.SRC_IN)
    imgTeam.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, teamColor))

}
fun hideShowPassword(passField: EditText, eyeIcon: ImageView) {
    /*eyeIcon.setOnClickListener {
        val transformMethod = passField.transformationMethod
        val isPassEnable = transformMethod is PasswordTransformationMethod
        eyeIcon.setImageDrawable(
            ContextCompat.getDrawable(
                passField.context,
                if (isPassEnable) R.drawable.ic_password_show else R.drawable.ic_password_hide
            )
        )
        passField.transformationMethod =
            if (isPassEnable) HideReturnsTransformationMethod() else PasswordTransformationMethod()
    }*/
}

fun Activity.setStatusBarColorAndIcon(
    statusBarColor: Int = R.color.colorTransparent, isLightIcons: Boolean
) {
    val decorView = window.decorView
    val wic = WindowInsetsControllerCompat(window, decorView)
    wic.isAppearanceLightStatusBars = !isLightIcons
    window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
}


fun Context.showErrorToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context.loadImage(url: String,imageView: ImageView) =  Glide.with(this)
    .load(url)
    .placeholder(R.drawable.ic_loading)
    .error(R.drawable.ic_logo)
    .into(imageView)
fun Context.loadProfileImage(url: String,imageView: ImageView) =

    Glide.with(this)
    .load(url)
    .placeholder(R.drawable.ic_loading)
    .error(R.drawable.dummy_profile)
    .apply(
        RequestOptions().placeholder(R.drawable.ic_loading)
            .error(R.drawable.dummy_profile)
        .transform(CenterCrop(), RoundedCorners(150))
    )
    .into(imageView)



fun showAlertDialogMessageError(context: Context,message:String,onclick:(type:String)->Unit ) {
    val builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
    builder.setTitle(ERROR_ALERT)
    builder.setMessage(message)
    builder.setPositiveButton("Ok") { dialogInterface, i -> dialogInterface.dismiss()
        onclick("ok")
    }
    builder.show()

}
 fun Context.showAlertDialogMessageError(message:String ) {
    val builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setTitle(ERROR_ALERT)
    builder.setMessage(message)
    builder.setPositiveButton("Ok") { dialogInterface, i -> dialogInterface.dismiss()
    }
    builder.show()

}
fun InvalidSession(context: Context, message: String) {
    showToast(context, message)
    showToast(context, ERROR_MESSAGE_INVALID_TOKEN)

    logoutFromApp(context as Activity)

}
fun logoutFromApp(activity: Activity ) {


    //FirebaseMessaging.getInstance().deleteToken()

    val preferenceManager = PreferenceManager(activity)
    preferenceManager.saveBoolean(KEY_CHECK_LOGIN, false)
    preferenceManager.clearPreferences()
    val intent = Intent(activity, AuthActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    activity.startActivity(intent)
    activity.finishAffinity()
}

fun clearHeapLimit(mContext: Context) {
    //   val deviceManger = mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
    if (BuildConfig.DEBUG) {
        val activityManager =
            mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager;
        if (SDK_INT >= Build.VERSION_CODES.M) {
            activityManager.clearWatchHeapLimit()
        }
    }
}
fun safeCall(callback: () -> Unit) {
    try {
        callback.invoke()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
fun getLastSegmentFromUrl(url: String): String? {
    val uri = Uri.parse(url)
    return uri.lastPathSegment
}
fun dateChangeInDMY(inpDate: String?): String {
    if (TextUtils.isEmpty(inpDate)) {
        return ""
    }

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(inpDate)
    val output = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    val d: Date? = inputFormat
    val formatted: String = output.format(d)
    return formatted

}
fun getHTMLFormatText(text: String?): Spanned {
    val message = if (TextUtils.isEmpty(text)) "" else text

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(message, Html.FROM_HTML_OPTION_USE_CSS_COLORS)
    } else {
        return Html.fromHtml(message)
    }
}

fun askIsNotificationPermission(context: Context): Boolean {
    // This is only necessary for API level >= 33 (TIRAMISU)
    var isGranted = false
    if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // FCM SDK (and your app) can post notifications.
            isGranted = true
        } else if (shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.POST_NOTIFICATIONS
            )
        ) {
            isGranted = true
            /*   TODO: display an educational UI explaining to the user the features that will be enabled
                     by them granting the POST_NOTIFICATION permission. This UI should provide the user
                     "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                     If the user selects "No thanks," allow the user to continue without notifications.*/
        } else {
            // Directly ask for the permission


        }

    }
    return isGranted;
}

fun loadImage( imageURL: String, imageView: ImageView) {
    Glide.with(imageView.getContext())
        .setDefaultRequestOptions(RequestOptions())
        .load(imageURL)
        .placeholder(R.drawable.ic_loading)
        .error(R.drawable.ic_logo)
        .into(imageView)
}


fun passWordEditText(context: Context, isVisiblePassword: Boolean, edPassword: EditText) {
    var isVisible = isVisiblePassword
    edPassword.setCompoundDrawablesWithIntrinsicBounds(
        0,
        0,
        R.drawable.ic_password_visibility_off,
        0
    )
    edPassword.setOnTouchListener(View.OnTouchListener { v, event ->
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= edPassword.getRight() - edPassword.getCompoundDrawables()
                    .get(DRAWABLE_RIGHT).getBounds().width()
            ) {
                edPassword.clearFocus()
                if (isVisible) {
                    isVisible = false
                    edPassword.transformationMethod = PasswordTransformationMethod()
                    edPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_password_visibility_off,
                        0
                    )
                } else {
                    isVisible = true
                    edPassword.transformationMethod = null
                    edPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_password_visibility,
                        0
                    )
                }
                // your action here

                return@OnTouchListener true
            }
        }
        false
    })

}

fun isValidPassword(password: String): Boolean {
    if (password.length < 8) return false
    if (password.filter { it.isDigit() }.firstOrNull() == null) return false
    if (password.filter { it.isLetter() }.filter { it.isUpperCase() }
            .firstOrNull() == null) return false
    if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
            .firstOrNull() == null) return false
    if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

    return true
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}
fun isNetworkConnected(activity: Activity): Boolean {
    val isConnected: Boolean
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network as Network?)
    isConnected =
        networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    if (!isConnected) {
        showSnackBar(activity, ERROR_NO_INTERNET_ALERT)
    }
    return isConnected
}

fun replaceFragment(context: Context, newFragment: Fragment?) {
    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    fragmentTransaction.replace(R.id.frame, newFragment!!, newFragment.tag)
    fragmentTransaction.commit()
}

fun addFragment(context: Context, newFragment: Fragment?) {
    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    fragmentTransaction.add(R.id.frame, newFragment!!, newFragment.tag)
    fragmentTransaction.commit()
}

fun backStackReplaceFragment(context: Context, newFragment: Fragment?) {
    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    fragmentTransaction.replace(R.id.frame, newFragment!!, newFragment.tag)
        .addToBackStack(newFragment.tag)
    fragmentTransaction.commitAllowingStateLoss()
}

fun backStackAddFragment(context: Context, newFragment: Fragment?) {
    val fragmentManager = (context as AppCompatActivity).supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    fragmentTransaction.add(R.id.frame, newFragment!!, newFragment.tag)
        .addToBackStack(newFragment.tag)
    fragmentTransaction.commitAllowingStateLoss()
}

fun <T : Serializable?> getSerializable(
    activity: Activity,
    name: String,
    clazz: Class<T>
): T {
    return if (SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        activity.intent.getSerializableExtra(name, clazz)!!
    else
        activity.intent.getSerializableExtra(name) as T
}

@SuppressLint("SimpleDateFormat")
public fun getWeekDayMonthTime(date: String?): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")//2022-11-25 05:26:45
    var testDate: Date? = null
    try {
        testDate = date?.let { sdf.parse(it) }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    val formatter = SimpleDateFormat("EEEE dd MMM | HH:mm", Locale.getDefault())
    return testDate?.let { formatter.format(it) }.toString()
}

public fun getTimeOpeningHrs(date: String?): String {
    val sdf = SimpleDateFormat("hh:mm aaa", Locale.getDefault())
    var testDate: Date? = null
    try {
        testDate = date?.let { sdf.parse(it) }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return testDate?.let { formatter.format(it) }.toString()
}



fun isStrongPassword(password: String): Boolean {
    /* val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
     return password.matches(regex)*/
    /* val PASSWORD_PATTERN = Pattern.compile(
         "^" +
                 "(?=.*[@#$%^&+=])" +  // at least 1 special character
                 "(?=\\S+$)" +  // no white spaces
                 ".{8,}" +  // at least 8 characters
                 "$"
     )*/
    if (password.length < 8) return false
    if (password.filter { it.isDigit() }.firstOrNull() == null) return false
    if (password.filter { it.isLetter() }.filter { it.isUpperCase() }
            .firstOrNull() == null) return false
    if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
            .firstOrNull() == null) return false
    if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

    return true
}

fun getBusinessHrsTimeOpeningHrs(date: String?): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    var testDate: Date? = null
    try {
        testDate = date?.let { sdf.parse(it) }
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    val formatter = SimpleDateFormat("hh:mm aaa", Locale.getDefault())
    return testDate?.let { formatter.format(it) }.toString()
}


fun dateChangeRattingInDDMMYYYY(inpuStrDate: String): String {
    val inputPattern = "yyyy-mm-dd hh:mm:ss"
    val outputPattern = "dd MMM yyyy"
    val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

    val date: Date?
    var str: String? = ""

    try {
        date = inputFormat.parse(inpuStrDate)
        str = date?.let { outputFormat.format(it) }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return str!!
}

fun showGallaryBottomModelSheet(context: Context, progressDialog: DialogManager) {
    val bindingDialog =
        DialogImageUploadBinding.inflate(LayoutInflater.from(context), null, false)
    //val sheetView: View =  LayoutInflater.from(context).inflate(R.layout.dialog_image_upload,null)

   val dialogImageUpload = BottomSheetDialog(context, R.style.GalleryDialog)
    dialogImageUpload.setContentView(bindingDialog.root)


    // checking for runtime permission
    checkFileAccessPermission(context)
    bindingDialog.imgClose.setOnClickListener {
        progressDialog.dismissDialog()
        dialogImageUpload.dismiss()

    }
    bindingDialog.layoutCamera.setOnClickListener {
        // Camera code here;
        dialogImageUpload.dismiss()

        //  UiUtils.takeNewPicture(context as Activity)
        ImagePicker.with(context as Activity)
            .crop()
            .cameraOnly()//Crop image(Optional), Check Customization for more option
            .compress(150)            //Final image size will be less than 1 MB(Optional)
            .createIntent { intent ->
                progressDialog.showProgressDialog("")
                context.startActivityForResult(intent, IMAGE_CROP_REQUEST_CODE)
                //    startForImageResult.launch(intent)
            }


    }
    bindingDialog.layoutGallery.setOnClickListener {
        dialogImageUpload.dismiss()

        ImagePicker.with(context as Activity)
            .crop()
            .galleryOnly()
            .compress(150)            //Final image size will be less than 1 MB(Optional)
            .createIntent { intent ->
                progressDialog.showProgressDialog("")
                context.startActivityForResult(intent, IMAGE_CROP_REQUEST_CODE)
                //  startForImageResult.launch(intent)
            }

    }
    dialogImageUpload.show()
}
fun showWalletError(context: Context,
                    onItemClick: (type: String,  dlg: BottomSheetDialog) -> Unit) {
    val dialog = BottomSheetDialog(context, R.style.GalleryDialog)
    val dialogBinding =
        DialogWalletBalanceErrorBinding.inflate(LayoutInflater.from(context), null, false)
    val sheetView = dialogBinding.root
    dialog.setContentView(sheetView)
    dialog.setCancelable(false)

    /*  val screenHeight = context.resources.displayMetrics.heightPixels
      val layoutParams = sheetView.layoutParams
      layoutParams.height = screenHeight
      sheetView.layoutParams = layoutParams

      // Set the bottom sheet to be fullscreen
      dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED*/





    dialogBinding.btnAdd.setOnClickListener {

        onItemClick("submit",  dialog)

    }

    dialog.show()

}

fun showOTPDialogBottom(
    context: Context, isCancelable: Boolean,emailMobile: String,
    onItemClick: (type: String, otp: String, dlg: BottomSheetDialog) -> Unit
) {
    val dialog = BottomSheetDialog(context, R.style.GalleryDialog)
    val dialogBinding =
        DialogVerifyOtpBinding.inflate(LayoutInflater.from(context), null, false)
    val sheetView = dialogBinding.root
    dialog.setContentView(sheetView)
    dialog.setCancelable(isCancelable)

    val screenHeight = context.resources.displayMetrics.heightPixels
    val layoutParams = sheetView.layoutParams
    layoutParams.height = screenHeight
    sheetView.layoutParams = layoutParams

    // Set the bottom sheet to be fullscreen
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.behavior.isDraggable = false
    if (!isCancelable){
        dialogBinding.imgBack.visibility=View.VISIBLE
    }

    dialogBinding.tvEnterLabel.text=context.getString(R.string.enter_the_otp_sent_to)+" "+emailMobile
    dialogBinding.imgBack.setOnClickListener {
        dialog.dismiss()

    }
    dialogBinding.tvResend.setOnClickListener {
        onItemClick("resend", "", dialog)

    }
    dialogBinding.tvChangeNumber.isVisible=isCancelable
    dialogBinding.view1.isVisible=isCancelable
    dialogBinding.tvChangeNumber.setOnClickListener {
        dialog.dismiss()

    }


    dialogBinding.pinview.setPinViewEventListener(object : Pinview.PinViewEventListener {

        override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
            val pin = pinview!!.value

            if (pin.length==4){
                dialogBinding.btnSubmit.isEnabled=true
                dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.button_background_global)
                dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorWhite))
            }else{
                dialogBinding.btnSubmit.isEnabled=false
                dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.button_background_disable)
                dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorBtn))


            }
        }
    })
    dialogBinding.btnSubmit.setOnClickListener {
        val otp = dialogBinding.pinview.value.toString()
        printLog("verify otp ",dialogBinding.pinview.value.toString())
        if (otp.length==4){

            dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.button_background_global)
            dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorWhite))
            dialogBinding.btnSubmit.isEnabled=true
        }else{

            dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.btn_selecter)
            dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorBtn))
            dialogBinding.btnSubmit.isEnabled=false
        }
        if (otp.length < 4) {
            showToast(context, context.getString(R.string.please_enter_4_digit_otp))
        } else {

            onItemClick("verify", dialogBinding.pinview.value.toString(), dialog)
        }

    }

    dialog.show()

}

fun showUpdateEmailMobileBottom(
    context: Context, isCancelable: Boolean,type: String,
    onItemClick: (type: String, country_code: String,emailMobile: String, dlg: BottomSheetDialog) -> Unit
) {
    val dialog = BottomSheetDialog(context, R.style.GalleryDialog)
    val dialogBinding =
        DialogUpdateProfileBinding.inflate(LayoutInflater.from(context), null, false)
    val sheetView = dialogBinding.root
    dialog.setContentView(sheetView)
    dialog.setCancelable(isCancelable)

 /*   val screenHeight = context.resources.displayMetrics.heightPixels
    val layoutParams = sheetView.layoutParams
    layoutParams.height = screenHeight
    sheetView.layoutParams = layoutParams

    // Set the bottom sheet to be fullscreen
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.behavior.isDraggable = false*/
    dialogBinding.imgClose.visibility=View.VISIBLE
    if (type.equals("email")){
        dialogBinding.tvTitle.text= context.getString(R.string.update_email)
        dialogBinding.txtInputEmail.isVisible=true
        dialogBinding.clvCountry.isVisible=false
    }else{
        dialogBinding.tvTitle.text= context.getString(R.string.update_phone_number)
        dialogBinding.txtInputEmail.isVisible=false
        dialogBinding.clvCountry.isVisible=true
    }

    dialogBinding.tvDontRecieve.isVisible=false


    dialogBinding.imgClose.setOnClickListener {
        dialog.dismiss()

    }


    dialogBinding.btnSubmit.setOnClickListener {
        if (type.equals("email")&&dialogBinding.ediEmail.text.toString().trim().isEmpty()){
            showToast(context,context.getString(R.string.enter_email_address))
            dialogBinding.ediEmail.requestFocus()
        }else if (type.equals("email")&& !isValidEmailId(dialogBinding.ediEmail.text.toString())){
            showToast(context,context.getString(R.string.enter_valid_email_address))
            dialogBinding.ediEmail.requestFocus()
        }else if (type.equals("phone")&&dialogBinding.ediPhone.text.toString().trim().isEmpty()){
            showToast(context,context.getString(R.string.enter_mobile_number))
            dialogBinding.ediPhone.requestFocus()
        }else if (type.equals("email")&& dialogBinding.ediPhone.text!!.length < 4){
            showToast(context,context.getString(R.string.enter_valid_phone_number))
            dialogBinding.ediPhone.requestFocus()
        }else{
            dialogBinding.ediEmail.clearFocus()
            dialogBinding.ediPhone.clearFocus()
          var emailMobile=""
          var countryCode=""
          if (type.equals("email")){
              emailMobile=dialogBinding.ediEmail.text.toString()
          }else{
              emailMobile=dialogBinding.ediPhone.text.toString()
              countryCode=dialogBinding.countryPickerView.selectedCountryCode.toString()
              if (!countryCode.contains("+")){
                  countryCode="+"+countryCode
              }
          }

            onItemClick("submit", countryCode,emailMobile , dialog)
        }





    }

    dialog.show()

}

fun showVerifyOTPEmailMobileBottom(
    context: Context, isCancelable: Boolean,emailMobile: String,
    onItemClick: (type: String, otp: String , dlg: BottomSheetDialog) -> Unit
) {
    val dialog = BottomSheetDialog(context, R.style.GalleryDialog)
    val dialogBinding =
        DialogUpdateProfileBinding.inflate(LayoutInflater.from(context), null, false)
    val sheetView = dialogBinding.root
    dialog.setContentView(sheetView)
    dialog.setCancelable(isCancelable)

    /*val screenHeight = context.resources.displayMetrics.heightPixels
    val layoutParams = sheetView.layoutParams
    layoutParams.height = screenHeight
    sheetView.layoutParams = layoutParams

    // Set the bottom sheet to be fullscreen
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.behavior.isDraggable = false*/
    dialogBinding.imgClose.visibility=View.VISIBLE



    dialogBinding.imgClose.setOnClickListener {
        dialog.dismiss()
    }
    dialogBinding.tvDontRecieve.setOnClickListener {
        onItemClick("resend", "", dialog)

    }
    dialogBinding.tvTitle.text=context.getString(R.string.verify_otp)
    dialogBinding.tvEnterLabel.text=context.getString(R.string.enter_the_otp_sent_to)+" "+emailMobile
    dialogBinding.tvEnterLabel.isVisible=true

    dialogBinding.txtInputEmail.isVisible=false
    dialogBinding.clvCountry.isVisible=false

    dialogBinding.tvDontRecieve.isVisible=true
    dialogBinding.pinview.isVisible=true
    dialogBinding.btnSubmit.text=context.getString(R.string.verify)
    dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.button_background_disable)
    dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorBtn))

    dialogBinding.pinview.setPinViewEventListener(object : Pinview.PinViewEventListener {

        override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
            val pin = pinview!!.value

            if (pin.length==4){
                dialogBinding.btnSubmit.isEnabled=true
                dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.button_background_global)
                dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorWhite))
            }else{
                dialogBinding.btnSubmit.isEnabled=false
                dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.button_background_disable)
                dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorBtn))


            }
        }
    })

    dialogBinding.btnSubmit.setOnClickListener {
        val otp = dialogBinding.pinview.value.toString()
        printLog("verify otp ",dialogBinding.pinview.value.toString())
        if (otp.length==4){

            dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.button_background_global)
            dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorWhite))
            dialogBinding.btnSubmit.isEnabled=true
        }else{

            dialogBinding.btnSubmit.background=context.getDrawable(R.drawable.btn_selecter)
            dialogBinding.btnSubmit.setTextColor(context.resources.getColor(R.color.colorBtn))
            dialogBinding.btnSubmit.isEnabled=false
        }
        if (otp.length < 4) {
            showToast(context, context.getString(R.string.please_enter_4_digit_otp))
        } else {

            onItemClick("verify", dialogBinding.pinview.value.toString(), dialog)
        }

    }

    dialog.show()

}

fun showDialogAddCard(
    context: Context,
    onItemClick: (name: String, cardParams: CardMultilineWidget, pgBar: ProgressBar, btnSubmit: Button, dlg: BottomSheetDialog) -> Unit
) {
    val dialog = BottomSheetDialog(context, R.style.GalleryDialog)
    val dialogBinding =
        DialogBottomAddCardBinding.inflate(LayoutInflater.from(context), null, false)
    val sheetView = dialogBinding.root
    dialog.setContentView(sheetView)
    dialog.setCancelable(false)

    val screenHeight = context.resources.displayMetrics.heightPixels
    val layoutParams = sheetView.layoutParams
    layoutParams.height = screenHeight
    sheetView.layoutParams = layoutParams

    // Set the bottom sheet to be fullscreen
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.behavior.isDraggable = false
    dialogBinding.cardInputWidget.postalCodeRequired=false
    dialogBinding.btnAdd.setOnClickListener {


        if (TextUtils.isEmpty(dialogBinding.ediName.text.toString().trim())) {
            dialogBinding.ediName.requestFocus()
            showAlertMessageError(
                context, context.getString(R.string.please_enter_card_holder_name))

        }else if (!dialogBinding.cardInputWidget.validateAllFields()) {
            showAlertMessageError(context, context  .getString(R.string.invalid_card))
        } else {
            dialogBinding.ediName.clearFocus()
            onItemClick(
                dialogBinding.ediName.text.toString(),
                dialogBinding.cardInputWidget,
                dialogBinding.progressBar,dialogBinding.btnAdd, dialog
            )
        }


    }


    dialog.show()

}

fun showPredictErrorDialog(
    context: Context,
    onItemClick: (name: String,  dlg: BottomSheetDialog) -> Unit
) {
    val dialog = BottomSheetDialog(context, R.style.GalleryDialog)
    val dialogBinding =
        DialogPredictErrorBinding.inflate(LayoutInflater.from(context), null, false)
    val sheetView = dialogBinding.root
    dialog.setContentView(sheetView)
    dialog.setCancelable(false)


    dialogBinding.btnSubmit.setOnClickListener {
        onItemClick("add",dialog)

    }


    dialog.show()

}

fun showWalletErrorDialog(
    context: Context,
    onItemClick: (name: String,  dlg: BottomSheetDialog) -> Unit
) {
    val dialog = BottomSheetDialog(context, R.style.GalleryDialog)
    val dialogBinding =
        DialogWalletBalanceErrorBinding.inflate(LayoutInflater.from(context), null, false)
    val sheetView = dialogBinding.root
    dialog.setContentView(sheetView)
    dialog.setCancelable(false)


    dialogBinding.btnAdd.setOnClickListener {
        onItemClick("add",dialog)

    }


    dialog.show()

}
fun showAddAmountDialog(
    context: Context,
    onItemClick: (name: String,  dlg: BottomSheetDialog) -> Unit
) {
    val dialog = BottomSheetDialog(context, R.style.GalleryDialog)
    val dialogBinding =
        DialogBottomAddAmountBinding.inflate(LayoutInflater.from(context), null, false)
    val sheetView = dialogBinding.root
    dialog.setContentView(sheetView)
    dialog.setCancelable(false)


    dialogBinding.imgClose.setOnClickListener {
        dialog.dismiss()

    }
    dialogBinding.tv1000.setOnClickListener {
        dialogBinding.ediAmount.setText(dialogBinding.tv1000.text.toString())

    }

    dialogBinding.tv1500.setOnClickListener {
        dialogBinding.ediAmount.setText(dialogBinding.tv1500.text.toString())

    }
    dialogBinding.tv2000.setOnClickListener {
        dialogBinding.ediAmount.setText(dialogBinding.tv2000.text.toString())

    }
    dialogBinding.tv2500.setOnClickListener {
        dialogBinding.ediAmount.setText(dialogBinding.tv2500.text.toString())

    }
    dialogBinding.btnAdd.setOnClickListener {
        onItemClick("add",dialog)

    }


    dialog.show()

}

fun showAlertMessageError(mContext: Context, message: String) {
    val builder = AlertDialog.Builder(mContext)
    builder.setCancelable(false)
    builder.setTitle(ERROR_ALERT)
    builder.setMessage(message)
    builder.setPositiveButton("Ok") { dialogInterface, i -> dialogInterface.dismiss() }
    builder.show()

}

fun setAddressFrom(context: Context, addresses: MutableList<Address>) {
    if (addresses.size > 0) {
        val address =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val houseNo = addresses[0].featureName
        val thoroughfare = addresses[0].thoroughfare
        val landmark = addresses[0].subLocality
        val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val zipcodeFromMap = addresses[0].postalCode

        val locality = houseNo + "," + landmark + "," + city

        printLog("setAddressFrom locality", locality + "")
        printLog("setAddressFrom address", address + "")

        val intent = Intent()
        intent.putExtra("landmark", landmark)
        intent.putExtra("state", state)
        intent.putExtra("country", country)
        intent.putExtra("address", address)
        intent.putExtra("suburb", city)
        intent.putExtra("zipcode", zipcodeFromMap)
        intent.putExtra("latitude", addresses[0].latitude)
        intent.putExtra("longitude", addresses[0].longitude)
        (context as Activity).setResult(LOCATION_MAP_ADD_REQUEST_CODE, intent)

    }
}

fun compressImage(context: Context, imageUri: String): String {
    val filePath = getRealPathFromURI(context, imageUri)
    var scaledBitmap: Bitmap? = null
    val options = BitmapFactory.Options()
    //      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
    options.inJustDecodeBounds = true
    var bmp = BitmapFactory.decodeFile(filePath, options)
    var actualHeight = options.outHeight
    var actualWidth = options.outWidth
    //      max Height and width values of the compressed image is taken as 816x612
    val maxHeight = 816.0f
    val maxWidth = 612.0f
    var imgRatio = actualWidth / actualHeight.toFloat()
    val maxRatio = maxWidth / maxHeight
    //      width and height values are set maintaining the aspect ratio of the image
    if (actualHeight > maxHeight || actualWidth > maxWidth) {
        if (imgRatio < maxRatio) {
            imgRatio = maxHeight / actualHeight
            actualWidth = (imgRatio * actualWidth).toInt()
            actualHeight = maxHeight.toInt()
        } else if (imgRatio > maxRatio) {
            imgRatio = maxWidth / actualWidth
            actualHeight = (imgRatio * actualHeight).toInt()
            actualWidth = maxWidth.toInt()
        } else {
            actualHeight = maxHeight.toInt()
            actualWidth = maxWidth.toInt()
        }
    }
    //      setting inSampleSize value allows to load a scaled down version of the original image
    options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
    //      inJustDecodeBounds set to false to load the actual bitmap
    options.inJustDecodeBounds = false
    //      this options allow android to claim the bitmap memory if it runs low on memory
    options.inPurgeable = true
    options.inInputShareable = true
    options.inTempStorage = ByteArray(16 * 1024)
    try { //          load the bitmap from its path
        bmp = BitmapFactory.decodeFile(filePath, options)
    } catch (exception: OutOfMemoryError) {
        exception.printStackTrace()
    }
    try {
        scaledBitmap =
            Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
    } catch (exception: OutOfMemoryError) {
        exception.printStackTrace()
    }
    val ratioX = actualWidth / options.outWidth.toFloat()
    val ratioY = actualHeight / options.outHeight.toFloat()
    val middleX = actualWidth / 2.0f
    val middleY = actualHeight / 2.0f
    val scaleMatrix = Matrix()
    scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
    val canvas = Canvas(scaledBitmap!!)
    canvas.setMatrix(scaleMatrix)
    canvas.drawBitmap(
        bmp,
        middleX - bmp.width / 2,
        middleY - bmp.height / 2,
        Paint(Paint.FILTER_BITMAP_FLAG)
    )
    //      check the rotation of the image and display it properly
    val exif: ExifInterface
    try {
        exif = ExifInterface(filePath!!)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, 0
        )
        printLog("EXIF", "Exif: $orientation")
        val matrix = Matrix()
        if (orientation == 6) {
            matrix.postRotate(90f)
            printLog("EXIF", "Exif: $orientation")
        } else if (orientation == 3) {
            matrix.postRotate(180f)
            printLog("EXIF", "Exif: $orientation")
        } else if (orientation == 8) {
            matrix.postRotate(270f)
            printLog("EXIF", "Exif: $orientation")
        }
        scaledBitmap = Bitmap.createBitmap(
            scaledBitmap, 0, 0,
            scaledBitmap.width, scaledBitmap.height, matrix,
            true
        )
    } catch (e: IOException) {
        e.printStackTrace()
    }
    var out: FileOutputStream? = null
    try {
        out = FileOutputStream(imageUri)
        //          write the compressed bitmap at the destination specified by filename.
        scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    return imageUri
}


private fun getRealPathFromURI(context: Context, contentURI: String): String? {
    val contentUri = Uri.parse(contentURI)
    val cursor: Cursor? =
        context.getContentResolver().query(contentUri, null, null, null, null)
    return if (cursor == null) {
        contentUri.path
    } else {
        cursor.moveToFirst()
        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        cursor.getString(index)
    }
}

fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val heightRatio =
            Math.round(height.toFloat() / reqHeight.toFloat())
        val widthRatio =
            Math.round(width.toFloat() / reqWidth.toFloat())
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    val totalPixels = width * height.toFloat()
    val totalReqPixelsCap = reqWidth * reqHeight * 2.toFloat()
    while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
        inSampleSize++
    }
    return inSampleSize
}

fun searchPlace(activity: Activity) {
    /*  val fields = Arrays.asList(
          Place.Field.ID,
          Place.Field.NAME,
          Place.Field.ADDRESS,
          Place.Field.LAT_LNG,
          Place.Field.ADDRESS_COMPONENTS
      )
      val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
          .setTypeFilter(TypeFilter.ADDRESS)
          .setCountry(Constants.COUNTRY_NAME)
          .build(activity)

      activity.startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

*/
}

fun adjustFontScale(context: Context, configuration: Configuration, scale: Float) {
    configuration.fontScale = scale
    val metrics: DisplayMetrics = context.getResources().getDisplayMetrics()
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm!!.defaultDisplay.getMetrics(metrics)
    metrics.scaledDensity = configuration.fontScale * metrics.density
    context.getResources().updateConfiguration(configuration, metrics)
}



fun prepareFilePartFromUri(partName: String, filePath: String): MultipartBody.Part? {
    var imgFileBody: MultipartBody.Part? = null

    if (filePath != "") {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        imgFileBody = MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
    return imgFileBody
}

fun setDateAndMonth(inputDate: String, tvDate: TextView, tvMonth: TextView) {
    // printLog("inputDate",inputDate)
    val inputPattern = "dd-MM-yyyy"
    val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
    val date: Date? = inputFormat.parse(inputDate)
    val day_of_month = SimpleDateFormat("dd").format(date)
    val month = SimpleDateFormat("MMM").format(date)
    tvDate.setText(day_of_month)
    tvMonth.setText(month)

}



