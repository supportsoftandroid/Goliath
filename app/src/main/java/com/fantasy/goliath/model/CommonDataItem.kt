package com.fantasy.goliath.model

import android.os.Parcel
import android.os.Parcelable

data class CommonDataItem(
    val title: String,
    val type: String,
    var is_selected: Boolean = false,
) : SuperCastClass(), Parcelable {

    // Constructor for Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    )

    constructor() : this("", "", false)

    // Write object values to parcel for storage
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeByte(if (is_selected) 1 else 0)
    }

    // Creator - used when un-parceling our parcel (creating the object)
    override fun describeContents(): Int {
        return 0
    }

    // De-serialize object
    companion object CREATOR : Parcelable.Creator<CommonDataItem> {
        override fun createFromParcel(parcel: Parcel): CommonDataItem {
            return CommonDataItem(parcel)
        }

        override fun newArray(size: Int): Array<CommonDataItem?> {
            return arrayOfNulls(size)
        }
    }
}