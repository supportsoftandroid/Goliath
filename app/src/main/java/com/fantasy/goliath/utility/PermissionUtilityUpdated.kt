package com.fantasy.goliath.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PermissionUtilityUpdated {
    private var context: Context? = null

    fun checkPermission(context: Context, permissionsRequired: HashMap<String, String>): Boolean {
        val permissionsNeeded = ArrayList<String>()
        this.context = context

        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {

            val permissionsList = ArrayList<String>()

            for (entry in permissionsRequired.entries) {
                println(entry.key + " = " + entry.value)
                if (addPermission(permissionsList, entry.value))
                    permissionsNeeded.add(entry.key)
            }

            if (permissionsList.size > 0) {
                if (permissionsNeeded.size > 0) {
                    // Need Rationale
                    val message =
                        StringBuilder("You need to grant access to " + permissionsNeeded.get(0))
                    for (i in 1 until permissionsNeeded.size)
                        message.append(", ").append(permissionsNeeded.get(i))
                    showMessageOKCancel(
                        message.toString()
                    ) { dialog, which ->
                        ActivityCompat.requestPermissions(
                            context as Activity, permissionsList.toTypedArray(),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                        )
                    }
                    return false
                }
                ActivityCompat.requestPermissions(
                    context as Activity, permissionsList.toTypedArray(),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                )
                return false
            }

            //     insertDummyContact();
            //        Toast.makeText(context, "All Permissions are Granted", Toast.LENGTH_LONG).show();
            return true
        }
        return true
    }

    private fun addPermission(permissionsList: MutableList<String>, permission: String): Boolean {
        if (context != null && ContextCompat.checkSelfPermission(
                context!!,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsList.add(permission)
            // Check for Rationale Option
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    (context as Activity?)!!,
                    permission
                )
            )
                return true
        }
        return false
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

    companion object {
        val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124
    }


}