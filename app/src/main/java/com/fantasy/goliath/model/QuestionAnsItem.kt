package com.fantasy.goliath.model

import android.os.Parcel
import android.os.Parcelable

data class QuestionAnsItem(
    val question: String,
    var your_answer: String,
    val correct_answer: String,

) : SuperCastClass(), Parcelable {

    // Constructor for Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),

    )

    constructor() : this("", "", "")

    // Write object values to parcel for storage
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeString(your_answer)
        parcel.writeString(correct_answer)

    }

    // Creator - used when un-parceling our parcel (creating the object)
    override fun describeContents(): Int {
        return 0
    }

    // De-serialize object
    companion object CREATOR : Parcelable.Creator<QuestionAnsItem> {
        override fun createFromParcel(parcel: Parcel): QuestionAnsItem {
            return QuestionAnsItem(parcel)
        }

        override fun newArray(size: Int): Array<QuestionAnsItem?> {
            return arrayOfNulls(size)
        }
    }
}