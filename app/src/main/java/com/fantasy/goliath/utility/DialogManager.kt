package com.fantasy.goliath.utility

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.fantasy.goliath.databinding.PopUpCustomProgressBinding


/**
 * Created by Choudhary 9166900279 on 19/04/2023.
 */
class DialogManager(private val context: Context) {
    private var aDProgress: AlertDialog? = null
    lateinit var customProgressBinding: PopUpCustomProgressBinding

    init {
        val builder = AlertDialog.Builder(context)
        customProgressBinding =
            PopUpCustomProgressBinding.inflate(LayoutInflater.from(context), null, false)

        /*  val view = LayoutInflater.from(context).inflate(R.layout.pop_up_custom_progress, null)*/
        builder.setCancelable(false)
        builder.setView(customProgressBinding.root)
        // builder.setView(view)

        // customProgressBinding.tvProgress.text = progress
        aDProgress = builder.create()
        aDProgress!!.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
        aDProgress!!.setCanceledOnTouchOutside(false)
        aDProgress!!.setCancelable(false)
    }

    fun showDialog() {
        if (aDProgress != null) {
            aDProgress?.show()
        }
    }


    fun showProgressDialog(progress: String?) {
        customProgressBinding.tvProgress.text = progress
        aDProgress!!.show()
    }

    fun dismissDialog() {
        if (aDProgress != null) {
            aDProgress!!.dismiss()
        }
    }

}