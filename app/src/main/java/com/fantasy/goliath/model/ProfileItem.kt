package com.fantasy.goliath.model

import android.os.Parcel
import android.os.Parcelable

data class ProfileItem(
    val title: String,
    val sub_title: String,
    var type: String,
) : SuperCastClass(), Parcelable {

    // Constructor for Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),

    )

    constructor() : this("", "","")

    // Write object values to parcel for storage
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(sub_title)
        parcel.writeString(type)

    }

    // Creator - used when un-parceling our parcel (creating the object)
    override fun describeContents(): Int {
        return 0
    }

    // De-serialize object
    companion object CREATOR : Parcelable.Creator<ProfileItem> {
        override fun createFromParcel(parcel: Parcel): ProfileItem {
            return ProfileItem(parcel)
        }

        override fun newArray(size: Int): Array<ProfileItem?> {
            return arrayOfNulls(size)
        }
    }
}