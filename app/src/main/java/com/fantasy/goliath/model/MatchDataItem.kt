package com.fantasy.goliath.model

import android.os.Parcel
import android.os.Parcelable

data class MatchDataItem(
    val team_a: String,
    val team_b: String,
    val match_type: String,

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
        parcel.writeString(team_a)
        parcel.writeString(team_b)
        parcel.writeString(match_type)

    }

    // Creator - used when un-parceling our parcel (creating the object)
    override fun describeContents(): Int {
        return 0
    }

    // De-serialize object
    companion object CREATOR : Parcelable.Creator<MatchDataItem> {
        override fun createFromParcel(parcel: Parcel): MatchDataItem {
            return MatchDataItem(parcel)
        }

        override fun newArray(size: Int): Array<MatchDataItem?> {
            return arrayOfNulls(size)
        }
    }
}